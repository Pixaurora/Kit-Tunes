package net.pixaurora.kit_tunes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.client.sounds.ChannelAccess.ChannelHandle;
import net.minecraft.sounds.SoundSource;
import net.pixaurora.kit_tunes.impl.music.progress.PolledListeningProgress;
import net.pixaurora.kit_tunes.impl.music.progress.SongProgressTracker;
import net.pixaurora.kit_tunes.impl.util.Pair;

public class MusicPolling implements SoundEventListener {
    private static List<Pair<SoundInstance, PolledListeningProgress>> TRACKS_TO_POLL = new ArrayList<>();
    private static List<Pair<ChannelHandle, PolledListeningProgress>> POLLED_TRACKS = new ArrayList<>();

    @Override
    public void onPlaySound(SoundInstance sound, WeighedSoundEvents soundSet, float range) {
        SoundSource source = sound.getSource();
        if (source == SoundSource.MUSIC || source == SoundSource.RECORDS) {
            PolledListeningProgress progress = EventHandling
                    .handleTrackStart(SoundEventsUtils.minecraftTypeToInternalType(sound.getSound().getLocation()));

            TRACKS_TO_POLL.add(Pair.of(sound, progress));
        }
    }

    public static void pollTrackProgress(Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel) {
        TRACKS_TO_POLL.removeIf((soundAndProgress) -> {
            Optional<ChannelHandle> channel = Optional.ofNullable(instanceToChannel.get(soundAndProgress.first()));

            if (channel.isPresent()) {
                POLLED_TRACKS.add(Pair.of(channel.get(), soundAndProgress.second()));

                return true;
            } else {
                return false;
            }
        });

        POLLED_TRACKS.removeIf((channelAndProgress) -> {
            if (channelAndProgress.first().isStopped()) {
                EventHandling.handleTrackEnd(channelAndProgress.second());

                return true;
            } else {
                channelAndProgress.first().execute(
                        channel -> channelAndProgress.second().measureProgress((SongProgressTracker) (Object) channel));

                return false;
            }
        });
    }
}
