package net.pixaurora.kit_tunes.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
import net.pixaurora.kit_tunes.api.event.TrackStartEvent;
import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.event.TrackEventImpl;
import net.pixaurora.kit_tunes.impl.music.progress.PolledListeningProgress;
import net.pixaurora.kit_tunes.impl.util.Pair;

public class EventHandling {
    private static final Map<ListeningProgress, Pair<ResourcePath, Optional<Track>>> PLAYING_TRACKS = new HashMap<>();
    private static List<Runnable> MAIN_THREAD_TASKS = new ArrayList<>();

    public static PolledListeningProgress handleTrackStart(ResourcePath path) {
        PolledListeningProgress progress = new PolledListeningProgress();

        KitTunes.EXECUTOR.execute(() -> {
            TrackStartEvent event = createStartEvent(path, progress);

            processEvent(listener -> listener.onTrackStart(event));
        });

        return progress;
    }

    public static void handleTrackEnd(ListeningProgress progress) {
        KitTunes.EXECUTOR.execute(() -> {
            handleTrackEnd(progress, getTrackInfo(progress, true));
        });
    }

    private static void handleTrackEnd(ListeningProgress progress, Pair<ResourcePath, Optional<Track>> pathAndTrack) {
        TrackEndEvent event = new TrackEventImpl(pathAndTrack.first(), pathAndTrack.second(), progress);

        processEvent(listener -> listener.onTrackEnd(event));
    }

    public static void init() {
    }

    public static synchronized void tick() {
        for (Runnable task : MAIN_THREAD_TASKS) {
            task.run();
        }

        MAIN_THREAD_TASKS.clear();
    }

    public static synchronized void stop() {
        KitTunes.LOGGER.info("Shutting down!");
        for (Map.Entry<ListeningProgress, Pair<ResourcePath, Optional<Track>>> entry : PLAYING_TRACKS.entrySet()) {
            handleTrackEnd(entry.getKey(), entry.getValue());
        }

        tick(); // Tick one last time to clear any remaining tasks out.
    }

    private static synchronized TrackStartEvent createStartEvent(ResourcePath path, ListeningProgress progress) {
        Optional<Track> track = MusicMetadata.matchTrack(path.toString());

        PLAYING_TRACKS.put(progress, Pair.of(path, track));

        return new TrackEventImpl(path, track, progress);
    }

    private static synchronized Pair<ResourcePath, Optional<Track>> getTrackInfo(ListeningProgress progress,
            boolean flushFromMap) {
        Pair<ResourcePath, Optional<Track>> trackInfo = Objects.requireNonNull(PLAYING_TRACKS.get(progress));

        if (flushFromMap) {
            PLAYING_TRACKS.remove(progress);
        }

        return trackInfo;
    }

    private static void processEvent(Consumer<MusicEventListener> event) {
        for (MusicEventListener listener : KitTunes.MUSIC_LISTENERS) {
            Runnable eventAction = () -> event.accept(listener);

            if (listener.isSynchronized()) {
                MAIN_THREAD_TASKS.add(eventAction);
            } else {
                KitTunes.EXECUTOR.execute(eventAction);
            }
        }
    }
}
