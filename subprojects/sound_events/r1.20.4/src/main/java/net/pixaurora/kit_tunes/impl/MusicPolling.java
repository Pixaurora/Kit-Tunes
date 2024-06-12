package net.pixaurora.kit_tunes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.impl.music.progress.PolledListeningProgress;
import net.pixaurora.kit_tunes.impl.music.progress.SongProgressTracker;

public class MusicPolling {
	private static List<PolledTrack> POLLED_TRACKS = new ArrayList<>();

	public static void trackStarted(SoundInstance sound, Track track) {
		POLLED_TRACKS.add(new PolledTrack(sound, track, new PolledListeningProgress()));
	}

	public static void pollTrackProgress(Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel) {
		List<PolledTrack> finishedTracks = new ArrayList<>();

		for (PolledTrack track : POLLED_TRACKS) {
			var channelHandle = Optional.ofNullable(instanceToChannel.get(track.key()));

			if (channelHandle.isPresent()) {
				channelHandle.get().execute(channel -> track.progress().measureProgress((SongProgressTracker)(Object) channel));
			} else {
				finishedTracks.add(track);
			}
		}

		for (PolledTrack finishedTrack : finishedTracks) {
			KitTunesEvents.onTrackEnd(finishedTrack.track, finishedTrack.progress);
			POLLED_TRACKS.remove(finishedTrack);
		}
	}

	private static record PolledTrack(SoundInstance key, Track track, PolledListeningProgress progress) {
	}
}
