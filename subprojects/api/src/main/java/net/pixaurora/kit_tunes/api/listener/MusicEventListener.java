package net.pixaurora.kit_tunes.api.listener;

import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;

public interface MusicEventListener {
	public void onTrackStart(Track track);

	public void onTrackEnd(Track track, ListeningProgress progress);
}
