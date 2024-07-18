package net.pixaurora.kit_tunes.impl.music.progress;

public interface SongProgressTracker {
    default float kit_tunes$playbackPosition() {
        throw new RuntimeException("No implementation for kit_tunes$getPlaybackPosition could be found.");
    };
}
