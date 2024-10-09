package net.pixaurora.kitten_heart.impl.music.progress;

import java.time.Duration;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressProvider;

public class PlayingSong implements ProgressProvider {
    private final Optional<Track> track;
    private final ListeningProgress progress;

    public PlayingSong(Optional<Track> track, ListeningProgress progress) {
        this.track = track;
        this.progress = progress;
    }

    public Optional<Track> track() {
        return this.track;
    }

    public ListeningProgress progress() {
        return this.progress;
    }

    @Override
    public double percentComplete() {
        return (double) this.playedDuration().toMillis() / this.totalDuration().toMillis();
    }

    @Override
    public Duration playedDuration() {
        return this.progress.amountPlayed();
    }

    @Override
    public Duration totalDuration() {
        if (this.track.isPresent()) {
            return this.track.get().duration();
        } else {
            return this.playedDuration();
        }
    }
}
