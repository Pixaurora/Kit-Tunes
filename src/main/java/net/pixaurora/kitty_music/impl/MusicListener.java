package net.pixaurora.kitty_music.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.pixaurora.kitty_music.impl.gui.MeowPlayingToast;
import net.pixaurora.kitty_music.impl.music.MusicPathConverter;
import net.pixaurora.kitty_music.impl.music.TrackInfo;

public class MusicListener implements SoundEventListener {
	private final Minecraft client;

	public MusicListener(Minecraft client) {
		this.client = client;
	}

	@Override
	public void onPlaySound(SoundInstance sound, WeighedSoundEvents soundSet, float range) {
		if (sound.getSource() == SoundSource.MUSIC) {
			TrackInfo track = MusicPathConverter.convertMusicPath(sound.getSound().getLocation());

			this.client.getToasts().addToast(new MeowPlayingToast(track));
		}
	}

}
