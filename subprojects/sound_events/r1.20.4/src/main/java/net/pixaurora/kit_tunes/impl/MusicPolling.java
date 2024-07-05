package net.pixaurora.kit_tunes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.ChannelAccess.ChannelHandle;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.impl.music.progress.PolledListeningProgress;
import net.pixaurora.kit_tunes.impl.music.progress.SongProgressTracker;

public class MusicPolling {
    private static List<TrackToPoll> TRACKS_TO_POLL = new ArrayList<>();
    private static List<PolledTrack> POLLED_TRACKS = new ArrayList<>();

    public static void trackStarted(SoundInstance sound, Track track) {
        TRACKS_TO_POLL.add(new TrackToPoll(sound, track));
    }

    public static void pollTrackProgress(Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel) {
        TRACKS_TO_POLL.removeIf((track) -> {
            Optional<ChannelHandle> channel = Optional.ofNullable(instanceToChannel.get(track.key));

            if (channel.isPresent()) {
                POLLED_TRACKS.add(new PolledTrack(channel.get(), track.track, new PolledListeningProgress()));

                return true;
            } else {
                return false;
            }
        });

        POLLED_TRACKS.removeIf((track) -> {
            if (track.channel.isStopped()) {
                KitTunesEvents.onTrackEnd(track.track, track.progress);

                return true;
            } else {
                track.channel
                        .execute(channel -> track.progress().measureProgress((SongProgressTracker) (Object) channel));

                return false;
            }
        });
    }

    private static record TrackToPoll(SoundInstance key, Track track) {

    }

    private static record PolledTrack(ChannelHandle channel, Track track, PolledListeningProgress progress) {
    }
}
