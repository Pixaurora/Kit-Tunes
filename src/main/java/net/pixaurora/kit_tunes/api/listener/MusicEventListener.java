package net.pixaurora.kit_tunes.api.listener;

import net.pixaurora.kit_tunes.impl.music.Track;
import net.pixaurora.kit_tunes.impl.music.progress.PlayingTrack;

public interface MusicEventListener {
	public void onTrackStart(Track track);

	public void onTrackEnd(PlayingTrack track);
}
