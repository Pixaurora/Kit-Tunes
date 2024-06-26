package net.pixaurora.kit_tunes.impl.mixin.sound_events;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.audio.Channel;

import net.pixaurora.kit_tunes.impl.music.progress.SongProgressTracker;

@Mixin(Channel.class)
public class ChannelMixin implements SongProgressTracker {
    @Shadow
    @Final
    private int source;

    private int completedBufferCount = 0;

    @Inject(method = "pumpBuffers", at = @At("HEAD"))
    private void trackCompletedBuffers(int bufferCount, CallbackInfo ci) {
        this.completedBufferCount += bufferCount;
    }

    private float trackedSeconds() {
        return Channel.BUFFER_DURATION_SECONDS * this.completedBufferCount;
    }

    @Override
    public float kit_tunes$playbackPosition() {
        return this.trackedSeconds() + AL10.alGetSourcef(this.source, AL11.AL_SEC_OFFSET);
    }
}
