package net.pixaurora.kitten_sounds.impl;

import java.util.concurrent.atomic.AtomicReference;

import net.pixaurora.kitten_heart.impl.music.control.MusicControls;
import net.pixaurora.kitten_heart.impl.music.control.PlaybackState;
import paulscode.sound.Source;

public class MusicControlsImpl implements MusicControls {
    private String sourceName;
    private Source source;
    private final AtomicReference<PlaybackState> playbackState = new AtomicReference<>(PlaybackState.STOPPED);

    public void source(String sourceName, Source source) {
        this.sourceName = sourceName;
        this.source = source;
    }

    @Override
    public void pause() {
        if (sourceName != null) {
            SoundEventsUtils.system().pause(sourceName);
        }
    }

    @Override
    public void unpause() {
        if (sourceName != null) {
            SoundEventsUtils.system().play(sourceName);
        }
    }

    @Override
    public PlaybackState playbackState() {
        return this.playbackState.get();
    }

    public void updatePlaybackState() {
        this.playbackState.set(computePlaybackState());
    }

    public PlaybackState computePlaybackState() {
        if (this.source == null || this.source.channel == null) {
            return PlaybackState.STOPPED;
        } else if (this.source.channel.playing()) {
            return PlaybackState.PLAYING;
        } else {
            return PlaybackState.PAUSED;
        }
    }
}
