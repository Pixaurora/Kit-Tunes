package net.pixaurora.kit_tunes.impl;

import java.nio.file.Path;

import org.quiltmc.loader.api.QuiltLoader;

public abstract class KitTunesConstants {
	public static final String MOD_ID = "kit_tunes";

	public static final Path SCROBBLER_CACHE_PATH = QuiltLoader.getCacheDir().resolve(Path.of(KitTunesConstants.MOD_ID, "scrobblers.json"));
}
