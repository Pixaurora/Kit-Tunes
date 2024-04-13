package net.pixaurora.kit_tunes.impl.scrobble;

import java.time.Instant;
import java.util.Optional;

public interface ScrobbleInfo {
	public String trackTitle();

	public String artistTitle();

	public Optional<String> albumTitle();

	public Instant startTime();
}
