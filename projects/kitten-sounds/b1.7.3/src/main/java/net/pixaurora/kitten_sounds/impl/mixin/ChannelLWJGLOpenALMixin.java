package net.pixaurora.kitten_sounds.impl.mixin;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;

import net.pixaurora.kitten_heart.impl.music.progress.SongProgressTracker;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.libraries.ChannelLWJGLOpenAL;

/**
 * The following code for calculating milliseconds that were played is adapted
 * from an updated version of Paulscode's Soundsystem.
 * All credit goes to the true authors.
 *
 * Author: Paul Lamb
 * Website: http://www.paulscode.com
 *
 */
@Mixin(ChannelLWJGLOpenAL.class)
public class ChannelLWJGLOpenALMixin implements SongProgressTracker {
    private float millisPreviouslyPlayed = 0;

    @Override
    public float kit_tunes$playbackPosition() {
        return this.millisPlayed() / 1000f;
    }

    private float millisPlayed() {
        // get number of samples played in current buffer
        float offset = (float) AL10.alGetSourcei(this.asChannel().ALSource.get(0), AL11.AL_BYTE_OFFSET);

        offset = (((float) offset / this.bytesPerFrame()) / (float) this.asChannel().sampleRate)
                * 1000;

        // add the milliseconds from stream-buffers that played previously
        if (this.asChannel().channelType == SoundSystemConfig.TYPE_STREAMING)
            offset += millisPreviouslyPlayed;

        // Return millis played:
        return offset;
    }

    private ChannelLWJGLOpenAL asChannel() {
        return (ChannelLWJGLOpenAL) (Object) this;
    }

    @Inject(method = "queueBuffer([B)Z", at = @At(value = "INVOKE", target = "Lorg/lwjgl/openal/AL10;alBufferData(IILjava/nio/ByteBuffer;I)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void beforeBuffersQueued(byte[] buffer, CallbackInfoReturnable<Boolean> cir, IntBuffer newBuffer) {
        if (AL10.alIsBuffer(newBuffer.get(0))) {
            millisPreviouslyPlayed += millisInBuffer(newBuffer.get(0));
        }

        this.asChannel().checkALError();
    }

    @ModifyExpressionValue(method = "feedRawAudioData([B)I", at = @At(value = "INVOKE", target = "Lpaulscode/sound/libraries/ChannelLWJGLOpenAL;errorCheck(ZLjava/lang/String;)Z"))
    public boolean beforeRawBuffersQueued(boolean errorCheck, @Local LocalRef<IntBuffer> newBuffer) {
        if (!errorCheck) {
            this.beforeRawBuffersQueued0(newBuffer.get());

            errorCheck = this.beforeRawBuffersQueued1(newBuffer);
        }

        return errorCheck;
    }

    private void beforeRawBuffersQueued0(IntBuffer newBuffer) {
        int i;
        newBuffer.rewind();
        while (newBuffer.hasRemaining()) {
            i = newBuffer.get();
            if (AL10.alIsBuffer(i)) {
                millisPreviouslyPlayed += millisInBuffer(i);
            }
            this.asChannel().checkALError();
        }
        AL10.alDeleteBuffers(newBuffer);
        this.asChannel().checkALError();
    }

    private boolean beforeRawBuffersQueued1(LocalRef<IntBuffer> newBuffer) {
        newBuffer.set(BufferUtils.createIntBuffer(1));
        AL10.alGenBuffers(newBuffer.get());

        return this.asChannel().errorCheck(this.asChannel().checkALError(),
                "Error generating stream buffers in method 'preLoadBuffers'");
    }

    @Inject(method = "flush()V", at = @At(value = "RETURN"))
    public void afterFlush(CallbackInfo cInfo) {
        this.millisPreviouslyPlayed = 0;
    }

    @ModifyExpressionValue(method = { "stop()V",
            "rewind()V" }, at = @At(value = "INVOKE", target = "Lpaulscode/sound/libraries/ChannelLWJGLOpenAL;checkALError()Z"))
    public boolean afterStop(boolean stopErrored) {
        if (!stopErrored) {
            millisPreviouslyPlayed = 0;
        }

        return stopErrored;
    }

    private float bytesPerFrame() {
        switch (this.asChannel().ALformat) {
            case AL10.AL_FORMAT_MONO8:
                return 1f;
            case AL10.AL_FORMAT_MONO16:
                return 2f;
            case AL10.AL_FORMAT_STEREO8:
                return 2f;
            case AL10.AL_FORMAT_STEREO16:
                return 4f;
            default:
                return 1f;
        }
    }

    /**
     * Returns the number of milliseconds of audio contained in specified buffer.
     *
     * @return milliseconds, or 0 if unable to calculate.
     */
    private float millisInBuffer(int alBufferi) {
        return (((float) AL10.alGetBufferi(alBufferi, AL10.AL_SIZE)
                / (float) AL10.alGetBufferi(alBufferi, AL10.AL_CHANNELS)
                / ((float) AL10.alGetBufferi(alBufferi, AL10.AL_BITS) / 8.0f) / (float) this.asChannel().sampleRate)
                * 1000);
    }
}
