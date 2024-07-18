package net.pixaurora.kit_tunes.impl.scrobble;

import java.time.Instant;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;

public class ScrobbledTrack implements ScrobbleInfo {
    private final Track track;
    private final Instant startTime;

    public ScrobbledTrack(Track track, ListeningProgress progress) {
        this.track = track;
        this.startTime = progress.startTime();
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
        return this.startTime;
    }
}
