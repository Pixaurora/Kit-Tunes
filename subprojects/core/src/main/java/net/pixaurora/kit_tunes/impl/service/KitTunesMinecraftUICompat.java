package net.pixaurora.kit_tunes.impl.service;

import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.ui.Component;
import net.pixaurora.kit_tunes.impl.ui.toast.KitTunesToastData;
import net.pixaurora.kit_tunes.impl.ui.toast.MeowPlayingToast;

/**
 * The version-specific implementation for pieces of the UI that are organized in the core of the mod.
 */
public interface KitTunesMinecraftUICompat {
	public static void sendNowPlayingNotification(Track track) {
		KitTunes.UI_LAYER.sendToast(new MeowPlayingToast(track));
	}

	public void sendToast(KitTunesToastData toast);

	public Component translatable(String key);

	public Component translatableWithFallback(String key, String defaultText);

	public Component literal(String text);
}
