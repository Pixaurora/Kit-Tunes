package net.pixaurora.kit_tunes.impl;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.pixaurora.kit_tunes.api.music.Track;

public class KitTunesSoundListener implements SoundEventListener {
	@Override
	public void onPlaySound(SoundInstance sound, WeighedSoundEvents soundSet, float range) {
		if (sound.getSource() == SoundSource.MUSIC) {
			Track track = KitTunesSoundEventsUtils.trackFromSound(sound);
			MusicPolling.trackStarted(sound, track);

			KitTunesEvents.onTrackStart(track);
		}
	}

}
