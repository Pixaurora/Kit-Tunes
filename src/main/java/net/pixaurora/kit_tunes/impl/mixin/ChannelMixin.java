package net.pixaurora.kit_tunes.impl.mixin;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.SOFTSourceLength;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.audio.Channel;

import net.pixaurora.kit_tunes.impl.music.progress.ProgressTrackingChannel;

@Mixin(Channel.class)
public class ChannelMixin implements ProgressTrackingChannel {
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

	@Override
	public float kit_tunes$playbackLength() {
		// This isn't entirely accurate yet as it just updates as the song plays.
		// A better way to query the track's length should be found, or maybe query the track's file.
		return this.trackedSeconds() + AL10.alGetSourcef(this.source, SOFTSourceLength.AL_SEC_LENGTH_SOFT);
	}
}
