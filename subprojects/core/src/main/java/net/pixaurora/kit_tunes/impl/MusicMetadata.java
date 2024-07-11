package net.pixaurora.kit_tunes.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public class MusicMetadata {
    private static List<Artist> ARTISTS;
    private static List<Album> ALBUMS;

    private static Map<String, Track> MATCH_TO_TRACK;

    public static void init() {
        ARTISTS = new ArrayList<>();
        ALBUMS = new ArrayList<>();
        MATCH_TO_TRACK = new HashMap<>();

        MusicMetadataLoading.loadMetadata();
    }

    public static void addArtist(Artist artist) {
        ARTISTS.add(artist);
    }

    public static void addAlbum(Album album) {
        ALBUMS.add(album);

        for (Track track : album.tracks()) {
            for (String match : track.matches()) {
                MATCH_TO_TRACK.put(match, track);
            }
        }
    }

    public static Artist findArtist(ResourcePath path) {
        for (Artist artist : ARTISTS) {
            if (path.representation().equals(artist.path().representation())) {
                return artist;
            }
        }

        throw new RuntimeException("Could not find artist at `" + path + "`!");
    }

    public static Optional<Track> matchTrack(String trackPath) {
        String[] splitPath = trackPath.split("/");
        String filename = splitPath[splitPath.length - 1];

        @Nullable
        Track track = MATCH_TO_TRACK.get(filename);

        return Optional.ofNullable(track);
    }
}
