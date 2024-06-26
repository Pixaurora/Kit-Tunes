package net.pixaurora.kit_tunes.impl;

import java.util.function.Consumer;

import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;

public class KitTunesEvents {
    private static void processEvent(Consumer<MusicEventListener> event) {
        for (MusicEventListener listener : KitTunes.MUSIC_LISTENERS) {
            Runnable eventAction = () -> event.accept(listener);

            if (listener.isSynchronized()) {
                eventAction.run();
            } else {
                KitTunes.EXECUTOR.execute(eventAction);
            }
        }
    }

    public static void onTrackStart(Track track) {
        processEvent(listener -> listener.onTrackStart(track));
    }

    public static void onTrackEnd(Track track, ListeningProgress progress) {
        processEvent(listener -> listener.onTrackEnd(track, progress));
    }
}
