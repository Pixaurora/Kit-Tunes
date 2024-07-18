package net.pixaurora.kit_tunes.impl.event;

import java.util.Optional;

import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
import net.pixaurora.kit_tunes.api.event.TrackStartEvent;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public class TrackEventImpl implements TrackStartEvent, TrackEndEvent {
    private final ResourcePath path;
    private final Optional<Track> track;
    private final ListeningProgress progress;

    public TrackEventImpl(ResourcePath path, Optional<Track> track, ListeningProgress progress) {
        this.path = path;
        this.track = track;
        this.progress = progress;
    }

    @Override
    public Optional<Track> track() {
        return this.track;
    }

    @Override
    public ResourcePath searchPath() {
        return this.path;
    }

    @Override
    public ListeningProgress progress() {
        return this.progress;
    }
}
