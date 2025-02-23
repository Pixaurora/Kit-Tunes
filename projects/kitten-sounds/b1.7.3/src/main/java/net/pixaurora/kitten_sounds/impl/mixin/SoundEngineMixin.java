package net.pixaurora.kitten_sounds.impl.mixin;

import java.util.Optional;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.client.sound.system.SoundEngine;
import net.minecraft.client.sound.system.SoundFile;
import net.minecraft.client.sound.system.Sounds;
import net.pixaurora.kitten_heart.impl.music.assets.Asset;
import net.pixaurora.kitten_heart.impl.music.assets.MusicAssetIndex;
import net.pixaurora.kitten_heart.impl.music.assets.MusicCategory;
import net.pixaurora.kitten_sounds.impl.KittenSounds;
import net.pixaurora.kitten_sounds.impl.MusicPolling;
import net.pixaurora.kitten_sounds.impl.SoundEventsUtils;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {
    @WrapOperation(method = "tickMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/system/Sounds;getRandom()Lnet/minecraft/client/sound/system/SoundFile;"))
    private SoundFile onBackgroundMusicQueued(Sounds instance, Operation<SoundFile> original) {
        SoundFile sound = chooseMusicOrFallback(
                index -> {
                    MusicCategory category = SoundEventsUtils.currentMusicCategory();

                    if (category == MusicCategory.OVERWORLD) {
                        return Optional.empty(); // Use Vanilla sound chooser instead.
                    }

                    return Optional.of(index.random(category));
                },
                original, instance);

        this.onSoundQueued(sound, "BgMusic");

        return sound;
    }

    @ModifyExpressionValue(method = "playRecord", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/system/Sounds;getRandom(Ljava/lang/String;)Lnet/minecraft/client/sound/system/SoundFile;"))
    private SoundFile onRecordQueued(SoundFile sound) {
        this.onSoundQueued(sound, "streaming");

        return sound;
    }

    /*
     * There's a short time between when we queue a song and it registers as
     * "playing."
     *
     * The sound engine also shouldn't tick music when we have a track paused,
     * as it will assume it stopped playing.
     *
     * Because of both of these, we skip ticking music cooldowns until all music has
     * stopped.
     */
    @ModifyExpressionValue(method = "tickMusic", at = @At(value = "FIELD", target = "Lnet/minecraft/client/sound/system/SoundEngine;started:Z", opcode = Opcodes.GETSTATIC))
    private boolean tickMusicCooldowns(boolean started) {
        return started && MusicPolling.TRACKS_TO_POLL.isEmpty() && MusicPolling.POLLED_TRACKS.isEmpty();
    }

    @Inject(method = "tickMusic", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo cInfo) {
        MusicPolling.pollTrackProgress();
    }

    private @Nullable SoundFile chooseMusicOrFallback(Function<MusicAssetIndex, Optional<Asset>> provideCustomMusic,
            Operation<SoundFile> original, Object... args) {
        if (!KittenSounds.ASSET_MANAGER.isReady()) {
            return null;
        }

        Optional<Asset> customMusic = tryToGetMusic(provideCustomMusic);

        if (customMusic.isPresent()) {
            return SoundEventsUtils.internalToMinecraftType(customMusic.get());
        } else {
            return original.call(args);
        }
    }

    private Optional<Asset> tryToGetMusic(Function<MusicAssetIndex, Optional<Asset>> provideCustomMusic) {
        MusicAssetIndex assetIndex = KittenSounds.ASSET_MANAGER.index();

        return provideCustomMusic.apply(assetIndex);
    }

    private void onSoundQueued(SoundFile sound, String source) {
        if (sound != null) {
            MusicPolling.onPlaySong(sound, source);
        }
    }
}
