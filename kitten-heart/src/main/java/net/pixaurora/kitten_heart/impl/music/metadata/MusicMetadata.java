package net.pixaurora.kitten_heart.impl.music.metadata;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.service.MusicMetadataService;

public final class MusicMetadata {
    private static @Nullable MusicMetadataService IMPL;

    public static void init(List<Path> albumFiles, List<Path> artistFiles) {
        IMPL = new MusicMetadataImpl();

        IMPL.load(albumFiles, artistFiles);
    }

    private static MusicMetadataService impl() {
        if (IMPL == null) {
            throw new RuntimeException("MusicMetadata was accessed too early!");
        } else {
            return IMPL;
        }
    }

    public static Optional<Artist> getArtist(ResourcePath path) {
        return impl().getArtist(path);
    }

    public static Optional<Track> matchTrack(ResourcePath soundPath) {
        return impl().matchTrack(soundPath);
    }
}
