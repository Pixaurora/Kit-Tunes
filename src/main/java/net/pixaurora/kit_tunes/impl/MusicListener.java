package net.pixaurora.kit_tunes.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.pixaurora.kit_tunes.impl.gui.MeowPlayingToast;
import net.pixaurora.kit_tunes.impl.scrobble.ScrobbledTrack;
import net.pixaurora.kit_tunes.impl.music.Track;

public class MusicListener implements SoundEventListener {
	private final Minecraft client;

	public MusicListener(Minecraft client) {
		this.client = client;
	}

	@Override
	public void onPlaySound(SoundInstance sound, WeighedSoundEvents soundSet, float range) {
		if (sound.getSource() == SoundSource.MUSIC) {
			Track track = MusicMetadata.matchTrack(sound.getSound().getLocation().toString());
			KitTunes.SCROBBLER_CACHE.execute(scrobblers -> scrobblers.startScrobbling(new ScrobbledTrack(track, 0.0f)));

			this.client.getToasts().addToast(new MeowPlayingToast(this.client.font, track));
		}
	}

}
