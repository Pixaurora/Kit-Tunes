package net.pixaurora.kit_tunes.impl.mixin.sound_events;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.MusicPolling;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @SuppressWarnings("resource")
    @Inject(method = "<init>", at = @At("TAIL"))
    public void addMusicListener(CallbackInfo ci) {
        Minecraft client = (Minecraft) (Object) this;
        client.getSoundManager().addListener(new MusicPolling());

        KitTunes.init();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        KitTunes.tick();
    }

    @Inject(method = "destroy", at = @At("HEAD"))
    public void onStop(CallbackInfo ci) {
        KitTunes.stop();
    }
}
