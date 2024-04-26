package net.pixaurora.kit_tunes.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.impl.gui.MeowPlayingToast;
import net.pixaurora.kit_tunes.impl.music.Track;
import net.pixaurora.kit_tunes.impl.resource.NamespacedResourcePath;
import net.pixaurora.kit_tunes.impl.service.KitTunesMinecraftCompat;

public class KitTunesImpl implements KitTunesMinecraftCompat {
	public static Track trackFromSound(SoundInstance sound) {
		return MusicMetadata.matchTrack(sound.getSound().getLocation().toString());
	}

	public static ResourceLocation convertPathToMinecraftFormat(NamespacedResourcePath path) {
		return new ResourceLocation(path.namespace(), path.path());
	}

	@Override
	public void sendMusicToast(Track track) {
		Minecraft client = Minecraft.getInstance();
		client.getToasts().addToast(new MeowPlayingToast(client.font, track));
	}
}
