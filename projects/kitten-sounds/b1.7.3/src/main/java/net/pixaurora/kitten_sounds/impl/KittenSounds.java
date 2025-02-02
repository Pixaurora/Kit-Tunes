package net.pixaurora.kitten_sounds.impl;

import java.io.IOException;

import net.pixaurora.kitten_heart.impl.music.assets.MusicAssetManager;

public class KittenSounds {
    public static MusicAssetManager ASSET_MANAGER;

    public static void init() {
        try {
            ASSET_MANAGER = MusicAssetManager.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load asset manager! ", e);
        }
    }
}
