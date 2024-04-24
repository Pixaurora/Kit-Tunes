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
		if (track.kit_tunes$playbackPosition() > 60.0) { // We use 60 seconds as the amount of time required to scrobble a song, for now.
			KitTunes.SCROBBLER_CACHE.execute(scrobblers -> scrobblers.completeScrobbling(new ScrobbledTrack(track.track(), track.startTime())));
		} else {
			KitTunes.LOGGER.info("Skipping scrobbling " + track.track().name() + " because it only played for " + track.kit_tunes$playbackPosition() + " seconds!");
		}
	}
}
