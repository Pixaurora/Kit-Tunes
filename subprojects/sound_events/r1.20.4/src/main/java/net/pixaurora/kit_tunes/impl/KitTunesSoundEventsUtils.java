package net.pixaurora.kit_tunes.impl;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.pixaurora.kit_tunes.impl.music.Track;

public class KitTunesSoundEventsUtils {
	public static Track trackFromSound(SoundInstance sound) {
		return MusicMetadata.matchTrack(sound.getSound().getLocation().toString());
	}
}
