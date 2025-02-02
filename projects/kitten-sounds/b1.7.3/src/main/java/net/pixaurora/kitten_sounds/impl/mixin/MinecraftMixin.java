package net.pixaurora.kitten_sounds.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_sounds.impl.KittenSounds;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @SuppressWarnings("resource")
    @Inject(method = "<init>", at = @At("TAIL"))
    public void addMusicListener(CallbackInfo ci) {
        KitTunes.init();

        KittenSounds.init();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        KitTunes.tick();
    }

    @Inject(method = "stop", at = @At("HEAD"))
    public void onStop(CallbackInfo ci) {
        KitTunes.stop();
    }
}
