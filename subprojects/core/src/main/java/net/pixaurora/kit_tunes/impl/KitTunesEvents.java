package net.pixaurora.kit_tunes.impl;

import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;

public class KitTunesEvents {
	public static void onTrackStart(Track track) {
		for (MusicEventListener listener : KitTunes.MUSIC_LISTENERS) {
			listener.onTrackStart(track);
		}
	}

	public static void onTrackEnd(Track track, ListeningProgress progress) {
		for (MusicEventListener listener : KitTunes.MUSIC_LISTENERS) {
			listener.onTrackEnd(track, progress);
		}
	}
}
