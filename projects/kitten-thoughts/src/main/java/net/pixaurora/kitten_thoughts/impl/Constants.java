package net.pixaurora.kitten_thoughts.impl;

import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;

public class Constants {
    public static final String MOD_ID = "kitten_thoughts";

    public static final String NATIVES_VERSION = "0.1.0";
    public static final String NATIVES_DIRECTORY_PROPERTY = "kitten_thoughts.natives_path";

    public static final Path NATIVES_CACHE_DIR = QuiltLoader.getCacheDir().resolve(MOD_ID);
}
