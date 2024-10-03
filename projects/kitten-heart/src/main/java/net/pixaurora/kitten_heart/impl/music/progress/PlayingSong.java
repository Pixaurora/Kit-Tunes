package net.pixaurora.kitten_heart.impl.music.progress;

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
        if (this.track.isPresent()) {
            return (double) this.progress.amountPlayed().toMillis() / this.track.get().duration().toMillis();
        } else {
            return 1.0;
        }
    }
}
