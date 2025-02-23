package net.pixaurora.kitten_sounds.impl;

import net.pixaurora.kitten_heart.impl.music.progress.PolledListeningProgress;

public class PolledSong<T> {
    private final T source;

    private final PolledListeningProgress progress;
    private final MusicControlsImpl controls;

    public PolledSong(T source, PolledSong<?> previous) {
        this.source = source;
        this.progress = previous.progress;
        this.controls = previous.controls;
    }

    public PolledSong(T source, PolledListeningProgress progress, MusicControlsImpl controls) {
        this.source = source;
        this.progress = progress;
        this.controls = controls;
    }

    public T polled() {
        return this.source;
    }

    public PolledListeningProgress progress() {
        return this.progress;
    }

    public MusicControlsImpl controls() {
        return this.controls;
    }
}
