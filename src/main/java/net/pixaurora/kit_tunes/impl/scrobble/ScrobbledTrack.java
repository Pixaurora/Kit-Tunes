package net.pixaurora.kit_tunes.impl.scrobble;

import java.time.Instant;
import java.util.Optional;

import net.pixaurora.kit_tunes.impl.music.Album;
import net.pixaurora.kit_tunes.impl.music.Track;

public class ScrobbledTrack implements ScrobbleInfo {
	private final Track track;
	private final float secondsPlayed;

	public ScrobbledTrack(Track track, float secondsPlayed) {
		this.track = track;
		this.secondsPlayed = secondsPlayed;
	}

	@Override
	public String trackTitle() {
		return this.track.name();
	}

	@Override
	public String artistTitle() {
		return this.track.artist().name();
	}

	@Override
	public Optional<String> albumTitle() {
		return this.track.album().map(Album::name);
	}

	@Override
	public Instant startTime() {
		throw new RuntimeException("No implementation for startTime could be found.");
	}
}
