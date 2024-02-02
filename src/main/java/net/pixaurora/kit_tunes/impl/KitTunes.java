package net.pixaurora.kit_tunes.impl;

import java.nio.file.Path;
import java.util.List;

import org.quiltmc.loader.api.QuiltLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.impl.config.ConfigManager;
import net.pixaurora.kit_tunes.impl.config.ScrobblerCache;

public class KitTunes {
	public static final String MOD_ID = "kit_tunes";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ConfigManager<ScrobblerCache> SCROBBLER_CACHE = new ConfigManager<>(
		QuiltLoader.getCacheDir().resolve(Path.of(MOD_ID, "scrobblers.json")),
		ScrobblerCache.CODEC,
		() -> new ScrobblerCache(List.of())
	);

	public static final ResourceLocation resource(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
