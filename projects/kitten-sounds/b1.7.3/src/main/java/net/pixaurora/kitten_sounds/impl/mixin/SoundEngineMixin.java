package net.pixaurora.kitten_sounds.impl.mixin;

import java.util.Optional;
import java.util.function.Function;

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
        // TODO: Properly extend the music chooser so that it's compatible with other
        // mods(?)
        SoundFile sound = chooseMusicOrFallback(
                index -> {
                    MusicCategory category = SoundEventsUtils.currentMusicCategory();

                    return Optional.of(index.random(category));
                },
                original, instance);

        this.onSoundQueued(sound, "BgMusic");

        return sound;
    }

    @WrapOperation(method = "playRecord", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/system/Sounds;getRandom(Ljava/lang/String;)Lnet/minecraft/client/sound/system/SoundFile;"))
    private SoundFile onRecordQueued(Sounds instance, String name, Operation<SoundFile> original) {
        SoundFile sound = chooseMusicOrFallback(index -> index.match(name), original, instance, name);

        this.onSoundQueued(sound, "streaming");

        return sound;
    }

    /*
     * There's a short time between when we queue a song and it registers as
     * "playing."
     * Because of this, we skip ticking music cooldowns until it's playing.
     */
    @ModifyExpressionValue(method = "tickMusic", at = @At(value = "FIELD", target = "Lnet/minecraft/client/sound/system/SoundEngine;started:Z", opcode = Opcodes.GETSTATIC))
    private boolean isWaitingForSongToStart(boolean started) {
        return started && MusicPolling.TRACKS_TO_POLL.isEmpty();
    }

    @Inject(method = "tickMusic", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo cInfo) {
        MusicPolling.pollTrackProgress();
    }

    private SoundFile chooseMusicOrFallback(Function<MusicAssetIndex, Optional<Asset>> provideCustomMusic,
            Operation<SoundFile> original, Object... args) {
        Optional<Asset> customMusic = tryToGetMusic(provideCustomMusic);

        if (customMusic.isPresent()) {
            return SoundEventsUtils.internalToMinecraftType(customMusic.get());
        } else {
            return original.call(args);
        }
    }

    private Optional<Asset> tryToGetMusic(Function<MusicAssetIndex, Optional<Asset>> provideCustomMusic) {
        Optional<MusicAssetIndex> assetIndex = KittenSounds.ASSET_MANAGER.index();

        if (!assetIndex.isPresent()) {
            return Optional.empty();
        }

        return provideCustomMusic.apply(assetIndex.get());
    }

    private void onSoundQueued(SoundFile sound, String source) {
        if (sound != null) {
            MusicPolling.onPlaySong(sound, source);
        }
    }
}
