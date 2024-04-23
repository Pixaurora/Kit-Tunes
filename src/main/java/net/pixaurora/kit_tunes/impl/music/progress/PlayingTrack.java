package net.pixaurora.kit_tunes.impl.music.progress;

import java.time.Instant;

import net.pixaurora.kit_tunes.impl.music.Track;

public class PlayingTrack implements SongProgressTracker {
	private final Track track;

	private float lastMeasuredTime;
	private final Instant startTime;

	public PlayingTrack(Track track) {
		this.track = track;
		this.lastMeasuredTime = 0.0f;
		this.startTime = Instant.now();
	}

	public Track track() {
		return this.track;
	}

	public void measureProgress(SongProgressTracker progress) {
		this.lastMeasuredTime = progress.kit_tunes$playbackPosition();
	}

	@Override
	public float kit_tunes$playbackPosition() {
		return this.lastMeasuredTime;
	}

	public Instant startTime() {
		return this.startTime;
	}
}
