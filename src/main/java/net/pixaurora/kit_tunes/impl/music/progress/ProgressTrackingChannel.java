package net.pixaurora.kit_tunes.impl.music.progress;

public interface ProgressTrackingChannel {
	default float kit_tunes$playbackPosition() {
		throw new RuntimeException("No implementation for kit_tunes$getPlaybackPosition could be found.");
	};

	default int kit_tunes$playbackLength() {
		throw new RuntimeException("No implementation for kit_tunes$getPlaybackLength could be found.");
	};
}
