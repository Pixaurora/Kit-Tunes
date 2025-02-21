package net.pixaurora.kitten_sounds.impl;

import java.net.URISyntaxException;
import java.nio.file.Paths;

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

    public static ResourcePath minecraftTypeToInternalType(SoundFile minecraft) {
        try {
            return minecraftToInternalType0(minecraft);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Malformed URI somehow!", e);
        }
    }

    public static ResourcePath minecraftToInternalType0(SoundFile minecraft) throws URISyntaxException {
        String fullyQualifiedPath = Paths.get(minecraft.url.toURI()).toAbsolutePath().toString();

        if (Minecraft.getOs() == Minecraft.OS.WINDOWS) {
            fullyQualifiedPath = fullyQualifiedPath.replaceAll("\\\\", "/");
        }

        return new ResourcePathImpl("", fullyQualifiedPath);
    }

    public static SoundFile internalToMinecraftType(Asset asset) {
        return UnhandledKitTunesException
                .runOrThrow(() -> new SoundFile(asset.path().toString(),
                        asset.path().toUri().toURL()));
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
