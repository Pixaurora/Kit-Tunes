package net.pixaurora.kit_tunes.api.music;

import java.time.Duration;
import java.time.Instant;

public interface ListeningProgress {
	public Instant startTime();

	public Duration amountPlayed();
}
