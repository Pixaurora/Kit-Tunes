package net.pixaurora.kitten_sounds.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.sound.system.SoundEngine;
import net.minecraft.client.sound.system.SoundFile;
import net.minecraft.world.World;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.error.UnhandledKitTunesException;
import net.pixaurora.kitten_heart.impl.music.assets.Asset;
import net.pixaurora.kitten_heart.impl.music.assets.MusicCategory;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathImpl;
import paulscode.sound.SoundSystem;

public class SoundEventsUtils {
    public static SoundSystem system() {
        return SoundEngine.system;
    }

    public static ResourcePath minecraftTypeToInternalType(String identifier) {
        return new ResourcePathImpl("", identifier);
    }

    public static SoundFile internalToMinecraftType(Asset asset) {
        return UnhandledKitTunesException
                .runOrThrow(() -> new SoundFile(asset.path().toString(), asset.path().toUri().toURL()));
    }

    public static MusicCategory currentMusicCategory() {
        World world = Minecraft.INSTANCE.world;

        if (world == null) {
            return MusicCategory.MAIN_MENU;
        } else if (world.dimension.isNether) {
            return MusicCategory.NETHER;
        } else {
            return MusicCategory.OVERWORLD;
        }
    }
}
