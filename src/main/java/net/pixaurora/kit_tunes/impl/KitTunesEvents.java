package net.pixaurora.kit_tunes.impl;

import java.time.Instant;

import net.pixaurora.kit_tunes.impl.music.Track;
import net.pixaurora.kit_tunes.impl.music.progress.PlayingTrack;
import net.pixaurora.kit_tunes.impl.scrobble.ScrobbledTrack;

public class KitTunesEvents {
	public static final KitTunesImpl LISTENER = new KitTunesImpl();

	public static void onTrackStart(Track track) {
		KitTunes.SCROBBLER_CACHE.execute(scrobblers -> scrobblers.startScrobbling(new ScrobbledTrack(track, Instant.now())));
	}

	public static void onTrackEnd(PlayingTrack track) {
		KitTunes.LOGGER.info("Track ended! Track name: " + track.track().name() + " Start time: " + track.startTime() + ", Seconds played: " + track.kit_tunes$playbackPosition());
	}
}
