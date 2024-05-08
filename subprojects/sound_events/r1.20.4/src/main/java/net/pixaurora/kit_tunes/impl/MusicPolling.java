package net.pixaurora.kit_tunes.impl;

import java.util.Map;
import java.util.Optional;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.impl.music.progress.PolledListeningProgress;
import net.pixaurora.kit_tunes.impl.music.progress.SongProgressTracker;

public class MusicPolling {
	private static Optional<PolledTrack> POLLING_INFO = Optional.empty();

	public static void trackStarted(SoundInstance sound, Track track) {
		POLLING_INFO = Optional.of(new PolledTrack(sound, track, new PolledListeningProgress()));
	}

	public static void pollTrackProgress(Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel) {
		if (POLLING_INFO.isPresent()) {
			PolledTrack pollingInfo = POLLING_INFO.get();

			var channelHandle = Optional.ofNullable(instanceToChannel.get(pollingInfo.key()));

			if (channelHandle.isPresent()) {
				channelHandle.get().execute(channel -> pollingInfo.progress().measureProgress((SongProgressTracker)(Object) channel));
			} else {
				POLLING_INFO = Optional.empty();

				KitTunesEvents.onTrackEnd(pollingInfo.track(), pollingInfo.progress());
			}
		}
	}

	private static record PolledTrack(SoundInstance key, Track track, PolledListeningProgress progress) {
	}
}
