package net.pixaurora.kit_tunes.impl.service;

import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.impl.KitTunes;

/**
 * The version-specific implementation for things that need to be called from the core of the mod.
 */
public interface KitTunesMinecraftCompat {
	public void sendMusicToast(Track track);

	public static void sendMusicNotification(Track track) {
		KitTunes.MINECRAFT_COMPAT.sendMusicToast(track);
	}
}
