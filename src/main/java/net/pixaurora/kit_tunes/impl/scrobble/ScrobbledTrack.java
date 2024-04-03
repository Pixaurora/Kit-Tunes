package net.pixaurora.kit_tunes.impl.scrobble;

import java.time.Instant;
import java.util.Optional;

import net.pixaurora.kit_tunes.impl.music.AlbumTrack;

public class ScrobbledTrack implements ScrobbleInfo {
	private final AlbumTrack track;
	private final float secondsPlayed;

	public ScrobbledTrack(AlbumTrack track, float secondsPlayed) {
		this.track = track;
		this.secondsPlayed = secondsPlayed;
	}

	@Override
	public String trackTitle() {
		throw new RuntimeException("No implementation for trackTitle could be found.");
	}

	@Override
	public String artist() {
		throw new RuntimeException("No implementation for artist could be found.");
	}

	@Override
	public Optional<String> albumTitle() {
		throw new RuntimeException("No implementation for albumTitle could be found.");
	}

	@Override
	public Instant startTime() {
		throw new RuntimeException("No implementation for startTime could be found.");
	}
}
