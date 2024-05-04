package net.pixaurora.kit_tunes.impl;

import java.util.Map;
import java.util.Optional;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.pixaurora.kit_tunes.impl.mixin.sound_events.ChannelMixin;
import net.pixaurora.kit_tunes.impl.music.Track;
import net.pixaurora.kit_tunes.impl.music.progress.PlayingTrack;

public class MusicPolling {
	private static Optional<PolledTrack> POLLING_INFO = Optional.empty();

	public static void trackStarted(SoundInstance sound, Track track) {
		POLLING_INFO = Optional.of(new PolledTrack(sound, new PlayingTrack(track)));
	}

	public static void pollTrackProgress(Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel) {
		if (POLLING_INFO.isPresent()) {
			PolledTrack pollingInfo = POLLING_INFO.get();

			var channelHandle = Optional.ofNullable(instanceToChannel.get(pollingInfo.key()));

			if (channelHandle.isPresent()) {
				channelHandle.get().execute(channel -> pollingInfo.track().measureProgress((ChannelMixin)(Object) channel));
			} else {
				POLLING_INFO = Optional.empty();

				KitTunesEvents.onTrackEnd(pollingInfo.track());
			}
		}
	}

	private static record PolledTrack(SoundInstance key, PlayingTrack track) {
	}
}
