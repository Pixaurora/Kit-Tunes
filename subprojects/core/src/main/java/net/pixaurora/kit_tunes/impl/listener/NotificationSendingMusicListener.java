package net.pixaurora.kit_tunes.impl.listener;

import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
import net.pixaurora.kit_tunes.api.event.TrackStartEvent;
import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.impl.service.KitTunesMinecraftUICompat;

public class NotificationSendingMusicListener implements MusicEventListener {
    @Override
    public void onTrackStart(TrackStartEvent event) {
        if (event.track().isPresent()) {
            KitTunesMinecraftUICompat.sendNowPlayingNotification(event.track().get());
        }
    }

    @Override
    public void onTrackEnd(TrackEndEvent event) {
    }

    @Override
    public boolean isSynchronized() {
        return true;
    }
}
