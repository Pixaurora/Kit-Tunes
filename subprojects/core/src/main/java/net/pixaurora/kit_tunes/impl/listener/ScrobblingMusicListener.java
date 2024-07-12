package net.pixaurora.kit_tunes.impl.listener;

import java.time.Duration;

import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
import net.pixaurora.kit_tunes.api.event.TrackStartEvent;
import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.scrobble.ScrobbledTrack;

public class ScrobblingMusicListener implements MusicEventListener {
    @Override
    public void onTrackStart(TrackStartEvent event) {
        if (!event.track().isPresent()) {
            return;
        }

        Track track = event.track().get();
        ListeningProgress progress = event.progress();

        KitTunes.SCROBBLER_CACHE.execute(scrobblers -> scrobblers.startScrobbling(new ScrobbledTrack(track, progress)));
    }

    @Override
    public void onTrackEnd(TrackEndEvent event) {
        if (!event.track().isPresent()) {
            return;
        }

        Track track = event.track().get();
        ListeningProgress progress = event.progress();

        boolean longEnoughToScrobble = progress.amountPlayed().compareTo(Duration.ofMinutes(1)) > 0;
        // We use 60 seconds as the amount of time required to scrobble a song, for now.

        if (longEnoughToScrobble) {
            KitTunes.SCROBBLER_CACHE
                    .execute(scrobblers -> scrobblers.completeScrobbling(new ScrobbledTrack(track, progress)));
        } else {
            KitTunes.LOGGER.info("Skipping scrobbling " + track.name() + " because it only played for "
                    + (float) progress.amountPlayed().toMillis() / 1000 + " seconds!");
        }
    }

}
