package net.pixaurora.kit_tunes.api.listener;

import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
import net.pixaurora.kit_tunes.api.event.TrackStartEvent;

public interface MusicEventListener {
    public default void onTrackStart(TrackStartEvent event) {
    }

    public default void onTrackEnd(TrackEndEvent event) {
    }

    public default boolean isSynchronized() {
        return false;
    }
}
