package net.pixaurora.kit_tunes.impl;

import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.impl.music.Track;
import net.pixaurora.kit_tunes.impl.music.progress.PlayingTrack;

public class KitTunesEvents {
	public static void onTrackStart(Track track) {
		for (MusicEventListener listener : KitTunes.MUSIC_LISTENERS) {
			listener.onTrackStart(track);
		}
	}

	public static void onTrackEnd(PlayingTrack track) {
		for (MusicEventListener listener : KitTunes.MUSIC_LISTENERS) {
			listener.onTrackEnd(track);
		}
	}
}
