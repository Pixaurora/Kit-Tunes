package net.pixaurora.kitten_heart.impl.music.metadata;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_heart.impl.service.MusicMetadataService;

public final class MusicMetadata {
    private static final String METADATA_PREFIX = "kit_tunes.metadata";
    private static final String SEPARATOR = ".";

    private static @Nullable MusicMetadataService IMPL;

    public static void init(List<Path> albumFiles, List<Path> artistFiles, List<Path> trackFiles) {
        IMPL = new MusicMetadataImpl();

        IMPL.load(albumFiles, artistFiles, trackFiles);
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

    public static Optional<Track> getTrack(ResourcePath path) {
        return impl().getTrack(path);
    }

    public static Optional<Track> matchTrack(ResourcePath soundPath) {
        return impl().matchTrack(soundPath);
    }

    public static List<Album> albumsWithTrack(Track track) {
        return impl().albumsWithTrack(track);
    }

    public static Component asComponent(Track track) {
        return Component.translatableWithFallback(translationKey(track.path()), track.name());
    }

    public static Component asComponent(Album album) {
        return Component.translatableWithFallback(translationKey(album.path()), album.name());
    }

    public static Component asComponent(Artist artist) {
        return Component.translatableWithFallback(translationKey(artist.path()), artist.name());
    }

    private static String translationKey(ResourcePath path) {
        String specifier = path.representation(SEPARATOR, SEPARATOR);

        return METADATA_PREFIX + SEPARATOR + specifier;
    }
}
