package net.pixaurora.kitten_sounds.impl;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.sound.system.SoundFile;
import net.pixaurora.kitten_heart.impl.EventHandling;
import net.pixaurora.kitten_heart.impl.music.progress.PolledListeningProgress;
import net.pixaurora.kitten_heart.impl.music.progress.SongProgressTracker;
import paulscode.sound.Source;

public class MusicPolling {
    public static List<PolledSong<String>> TRACKS_TO_POLL = new ArrayList<>();
    public static List<PolledSong<Source>> POLLED_TRACKS = new ArrayList<>();

    public static void onPlaySong(SoundFile sound, String source) {
        MusicControlsImpl controls = new MusicControlsImpl();

        PolledListeningProgress progress = EventHandling
                .handleTrackStart(SoundEventsUtils.minecraftTypeToInternalType(sound), controls);

        TRACKS_TO_POLL.add(new PolledSong<String>(source, progress, controls));
    }

    public static void pollTrackProgress() {
        TRACKS_TO_POLL.removeIf((polledSong) -> {
            Source source = SoundEventsUtils.system().soundLibrary.getSource(polledSong.polled());
            if (source != null && source.playing()) {
                POLLED_TRACKS.add(new PolledSong<Source>(source, polledSong));
                polledSong.controls().source(polledSong.polled(), source);
                return true;
            } else {
                return false;
            }
        });

        POLLED_TRACKS.removeIf((polledSong) -> {
            boolean songConnected = polledSong.polled().playing() || polledSong.polled().paused();
            if (!songConnected) {
                EventHandling.handleTrackEnd(polledSong.progress());

                return true;
            } else {
                polledSong.progress().measureProgress((SongProgressTracker) (Object) polledSong.polled().channel);
                polledSong.controls().updatePlaybackState();

                return false;
            }

        });
    }
}
