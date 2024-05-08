package net.pixaurora.kit_tunes.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.NamespacedResourcePath;
import net.pixaurora.kit_tunes.impl.gui.MeowPlayingToast;
import net.pixaurora.kit_tunes.impl.service.KitTunesMinecraftCompat;

public class KitTunesImpl implements KitTunesMinecraftCompat {
	public static ResourceLocation convertPathToMinecraftFormat(NamespacedResourcePath path) {
		return new ResourceLocation(path.namespace(), path.path());
	}

	public static ResourceLocation resource(String path) {
		return new ResourceLocation(KitTunesConstants.MOD_ID, path);
	}

	@Override
	public void sendMusicToast(Track track) {
		Minecraft client = Minecraft.getInstance();
		client.getToasts().addToast(new MeowPlayingToast(client.font, track));
	}
}
