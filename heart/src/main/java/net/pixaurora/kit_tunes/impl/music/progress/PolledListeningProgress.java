package net.pixaurora.kit_tunes.impl.music.progress;

import java.time.Duration;
import java.time.Instant;

import net.pixaurora.kit_tunes.api.music.ListeningProgress;

public class PolledListeningProgress implements ListeningProgress {
    private float lastMeasuredTime;
    private final Instant startTime;

    public PolledListeningProgress() {
        this.lastMeasuredTime = 0.0f;
        this.startTime = Instant.now();
    }

    public synchronized void measureProgress(SongProgressTracker progress) {
        this.lastMeasuredTime = progress.kit_tunes$playbackPosition();
    }

    @Override
    public Instant startTime() {
        return this.startTime;
    }

    @Override
    public synchronized Duration amountPlayed() {
        long nanoSecondsPlayed = (long) (this.lastMeasuredTime * Duration.ofSeconds(1).toNanos());
        return Duration.ofNanos(nanoSecondsPlayed);
    }
}
