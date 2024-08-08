package net.pixaurora.kitten_heart.impl.listener;

import java.util.Optional;

import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
import net.pixaurora.kit_tunes.api.event.TrackStartEvent;
import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kitten_heart.impl.ui.toast.MeowPlayingToast;
import net.pixaurora.kitten_heart.impl.ui.toast.smart.SmartToast;

public class NotificationSendingMusicListener implements MusicEventListener {
    @Override
    public void onTrackStart(TrackStartEvent event) {
        Optional<Track> track = event.track();
        ListeningProgress progress = event.progress();

        if (track.isPresent()) {
            this.sendTrackNotification(track.get(), progress);
        }
    }

    @Override
    public void onTrackEnd(TrackEndEvent event) {
    }

    private void sendTrackNotification(Track track, ListeningProgress progress) {
        SmartToast.queue(new MeowPlayingToast(track, progress));
    }

    @Override
    public boolean isSynchronized() {
        return true;
    }
}
