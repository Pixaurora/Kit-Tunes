package net.pixaurora.kitten_heart.impl;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.Optional;

import net.pixaurora.catculator.api.music.SoundFile;
import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
import net.pixaurora.kit_tunes.api.event.TrackStartEvent;
import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_heart.impl.error.UnhandledKitTunesException;
import net.pixaurora.kitten_heart.impl.event.TrackEventImpl;
import net.pixaurora.kitten_heart.impl.music.metadata.MusicMetadata;
import net.pixaurora.kitten_heart.impl.music.progress.PlayingSong;
import net.pixaurora.kitten_heart.impl.music.progress.PolledListeningProgress;
import net.pixaurora.kitten_heart.impl.resource.temp.FileAccess;
import net.pixaurora.kitten_heart.impl.util.Pair;

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

    public static void tick() {
        synchronized (MAIN_THREAD_TASKS) {
            for (Runnable task : MAIN_THREAD_TASKS) {
                task.run();
            }

            MAIN_THREAD_TASKS.clear();
        }
    }

    public static void addMainThreadTask(Runnable task) {
        synchronized (MAIN_THREAD_TASKS) {
            MAIN_THREAD_TASKS.add(task);
        }
    }

    public static boolean isTracking(ListeningProgress progress) {
        synchronized (PLAYING_TRACKS) {
            return PLAYING_TRACKS.containsKey(progress);
        }
    }

    public static boolean isTrackingAnything() {
        synchronized (PLAYING_TRACKS) {
            return PLAYING_TRACKS.size() > 0;
        }
    }

    public static void stop() {
        synchronized (PLAYING_TRACKS) {
            PLAYING_TRACKS.forEach(EventHandling::handleTrackEnd);
        }

        tick(); // Tick one last time to clear any remaining tasks out.
    }

    public static Collection<PlayingSong> playingSongs() {
        synchronized (PLAYING_TRACKS) {
            List<PlayingSong> songs = new ArrayList<>();

            for (Map.Entry<ListeningProgress, Pair<ResourcePath, Optional<Track>>> entry : PLAYING_TRACKS.entrySet()) {
                songs.add(new PlayingSong(entry.getValue().second(), entry.getKey()));
            }

            return songs;
        }
    }

    private static TrackStartEvent createStartEvent(ResourcePath path, ListeningProgress progress) {
        Optional<Track> track = MusicMetadata.matchTrack(path);

        if (track.isPresent()) {
            Duration songDuration = UnhandledKitTunesException.runOrThrow(() -> songDuration(path));

            MusicMetadata.asMutable().giveDuration(track.get(), songDuration);
        }

        synchronized (PLAYING_TRACKS) {
            PLAYING_TRACKS.put(progress, Pair.of(path, track));
        }

        return new TrackEventImpl(path, track, progress);
    }

    private static Duration songDuration(ResourcePath path) throws IOException {
        try (FileAccess resource = MinecraftClient.accessResource(path)) {
            return SoundFile.parseDuration(resource.path());
        }
    }

    private static Pair<ResourcePath, Optional<Track>> getTrackInfo(ListeningProgress progress,
            boolean flushFromMap) {
        synchronized (PLAYING_TRACKS) {
            Pair<ResourcePath, Optional<Track>> trackInfo = Objects.requireNonNull(PLAYING_TRACKS.get(progress));

            if (flushFromMap) {
                PLAYING_TRACKS.remove(progress);
            }

            return trackInfo;
        }
    }

    private static void processEvent(Consumer<MusicEventListener> event) {
        for (MusicEventListener listener : KitTunes.MUSIC_LISTENERS) {
            Runnable eventAction = () -> event.accept(listener);

            if (listener.isSynchronized()) {
                addMainThreadTask(eventAction);
            } else {
                KitTunes.EXECUTOR.execute(eventAction);
            }
        }
    }
}
