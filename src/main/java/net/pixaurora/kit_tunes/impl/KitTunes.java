package net.pixaurora.kit_tunes.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.impl.config.ConfigManager;
import net.pixaurora.kit_tunes.impl.config.ScrobblerCache;
import net.pixaurora.kit_tunes.impl.service.KitTunesMinecraftCompat;
import net.pixaurora.kit_tunes.impl.service.ServiceLoading;

public class KitTunes {
	public static final Logger LOGGER = LoggerFactory.getLogger(KitTunesConstants.MOD_ID);

	public static final ConfigManager<ScrobblerCache> SCROBBLER_CACHE = new ConfigManager<>(
		KitTunesConstants.SCROBBLER_CACHE_PATH,
		ScrobblerCache.class,
		() -> new ScrobblerCache(List.of())
	);

	public static final KitTunesMinecraftCompat MINECRAFT_COMPAT = ServiceLoading.loadJustOneOrThrow(KitTunesMinecraftCompat.class);

	public static final List<MusicEventListener> MUSIC_LISTENERS = ServiceLoading.loadAll(MusicEventListener.class);

	public static final ResourceLocation resource(String path) {
		return new ResourceLocation(KitTunesConstants.MOD_ID, path);
	}
}
