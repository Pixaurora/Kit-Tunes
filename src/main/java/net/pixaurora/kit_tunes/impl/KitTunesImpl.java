package net.pixaurora.kit_tunes.impl;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.impl.music.Track;
import net.pixaurora.kit_tunes.impl.resource.NamespacedResourcePath;

public class KitTunesImpl {
	public static Track trackFromSound(SoundInstance sound) {
		return MusicMetadata.matchTrack(sound.getSound().getLocation().toString());
	}

	public static ResourceLocation convertPathToMinecraftFormat(NamespacedResourcePath path) {
		return new ResourceLocation(path.namespace(), path.path());
	}
}
