package net.pixaurora.kit_tunes.impl.mixin;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.SOFTSourceLength;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.blaze3d.audio.Channel;

import net.pixaurora.kit_tunes.impl.music.progress.ProgressTrackingChannel;

@Mixin(Channel.class)
public class ChannelMixin implements ProgressTrackingChannel {
	@Shadow
	@Final
	private int source;

	@Override
	public float kit_tunes$playbackPosition() {
		return AL10.alGetSourcef(this.source, AL11.AL_SEC_OFFSET);
	}

	@Override
	public int kit_tunes$playbackLength() {
		return AL10.alGetSourcei(this.source, SOFTSourceLength.AL_SEC_LENGTH_SOFT);
	}
}
