package net.pixaurora.kitten_sounds.impl;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.sound.system.SoundFile;
import net.pixaurora.kitten_heart.impl.EventHandling;
import net.pixaurora.kitten_heart.impl.music.progress.PolledListeningProgress;

public class MusicPolling {
    public static List<PolledSong> TRACKS_TO_POLL = new ArrayList<>();
    public static List<PolledSong> POLLED_TRACKS = new ArrayList<>();

    public static void onPlaySong(SoundFile sound, String source) {
        MusicControlsImpl controls = new MusicControlsImpl();

        PolledListeningProgress progress = EventHandling
                .handleTrackStart(SoundEventsUtils.minecraftTypeToInternalType(sound), controls);

        TRACKS_TO_POLL.add(new PolledSong(source, progress, controls));
    }

    public static void pollTrackProgress() {
        TRACKS_TO_POLL.removeIf((polledSong) -> {
            if (SoundEventsUtils.system().playing(polledSong.polled())) {
                POLLED_TRACKS.add(polledSong);
                return true;
            } else {
                return false;
            }
        });

        POLLED_TRACKS.removeIf((polledSong) -> {
            if (!SoundEventsUtils.system().playing(polledSong.polled())) {
                EventHandling.handleTrackEnd(polledSong.progress());

                return true;
            } else {
                polledSong.progress().measureProgress(polledSong);
                polledSong.controls().updatePlaybackState();

                return false;
            }

        });
    }
}
