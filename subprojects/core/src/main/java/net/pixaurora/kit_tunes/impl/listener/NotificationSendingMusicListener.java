package net.pixaurora.kit_tunes.impl.listener;

import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.impl.service.KitTunesMinecraftUICompat;

public class NotificationSendingMusicListener implements MusicEventListener {
    @Override
    public void onTrackStart(Track track) {
        KitTunesMinecraftUICompat.sendNowPlayingNotification(track);
    }

    @Override
    public void onTrackEnd(Track track, ListeningProgress progress) {
    }
}
