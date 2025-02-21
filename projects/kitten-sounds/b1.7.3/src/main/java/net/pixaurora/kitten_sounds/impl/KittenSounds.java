package net.pixaurora.kitten_sounds.impl;

import java.io.File;
import java.io.IOException;
import java.util.function.BiConsumer;

import net.minecraft.client.Minecraft;
import net.pixaurora.kitten_heart.impl.music.assets.Asset;
import net.pixaurora.kitten_heart.impl.music.assets.MusicAssetManager;
import net.pixaurora.kitten_heart.impl.music.assets.MusicCategory;

public class KittenSounds {
    public static MusicAssetManager ASSET_MANAGER;

    public static void init() {
        try {
            ASSET_MANAGER = MusicAssetManager.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load asset manager! ", e);
        }
    }

    public static void registerMusic() {
        registerMusic0(Minecraft.INSTANCE.soundSystem::loadMusic, MusicCategory.OVERWORLD);

        registerMusic0(Minecraft.INSTANCE.soundSystem::loadRecord, MusicCategory.RECORDS);
    }

    public static void registerMusic0(BiConsumer<String, File> assetRegister, MusicCategory category) {
        for (Asset asset : ASSET_MANAGER.index().getAssets(category)) {
            String path = asset.path().getFileName().toString();
            File file = asset.path().toAbsolutePath().toFile();

            assetRegister.accept(path, file);
        }
    }

    public static void tickMusicInMenu() {
        if (SoundEventsUtils.currentMusicCategory() == MusicCategory.MAIN_MENU) {
            Minecraft.INSTANCE.soundSystem.tickMusic();
        }
    }
}
