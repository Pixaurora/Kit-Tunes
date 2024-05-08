package net.pixaurora.kit_tunes.impl.listener;

import java.time.Duration;
import java.time.Instant;

import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.scrobble.ScrobbledTrack;

public class ScrobblingMusicListener implements MusicEventListener {
	@Override
	public void onTrackStart(Track track) {
		KitTunes.SCROBBLER_CACHE.execute(scrobblers -> scrobblers.startScrobbling(new ScrobbledTrack(track, Instant.now())));
	}

	@Override
	public void onTrackEnd(Track track, ListeningProgress progress) {
		boolean longEnoughToScrobble = progress.amountPlayed().compareTo(Duration.ofMinutes(1)) > 0; // We use 60 seconds as the amount of time required to scrobble a song, for now.

		if (longEnoughToScrobble) {
			KitTunes.SCROBBLER_CACHE.execute(scrobblers -> scrobblers.completeScrobbling(new ScrobbledTrack(track, progress.startTime())));
		} else {
			KitTunes.LOGGER.info("Skipping scrobbling " + track.name() + " because it only played for " + progress.amountPlayed().toSeconds() + " seconds!");
		}
	}

}
