package net.pixaurora.kit_tunes.impl.listener;

import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.impl.service.KitTunesMinecraftCompat;

public class NotificationSendingMusicListener implements MusicEventListener {
	@Override
	public void onTrackStart(Track track) {
		KitTunesMinecraftCompat.sendMusicNotification(track);
	}

	@Override
	public void onTrackEnd(Track track, ListeningProgress progress) {
	}
}
