package net.pixaurora.catculator.impl;

import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;

public class Constants {
    public static final String PARENT_MOD_ID = "kit_tunes";
    public static final String MOD_ID = "catculator";

    public static final String NATIVES_VERSION = "0.2.0";
    public static final String NATIVES_DIRECTORY_PROPERTY = "catculator.natives_path";

    public static final Path NATIVES_CACHE_DIR = QuiltLoader.getCacheDir().resolve(PARENT_MOD_ID);
}
