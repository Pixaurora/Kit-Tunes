package net.pixaurora.kitten_star.impl.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import net.pixaurora.kitten_star.impl.MusicPolling;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {
    @Shadow
    @Final
    public Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel;

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo cInfo) {
        MusicPolling.pollTrackProgress(instanceToChannel);
    }
}
