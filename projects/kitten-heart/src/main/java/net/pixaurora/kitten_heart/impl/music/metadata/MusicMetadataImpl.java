package net.pixaurora.kitten_heart.impl.music.metadata;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.service.MusicMetadataService;

public class MusicMetadataImpl implements MusicMetadataService, MutableMusicMetadata {
    private final List<Album> albums = new ArrayList<>();
    private final List<Artist> artists = new ArrayList<>();
    private final List<Track> tracks = new ArrayList<>();

    private final HashMap<String, Track> trackMatches = new HashMap<>();

    @Override
    public void add(Album album) {
        this.albums.add(album);
    }

    @Override
    public void add(Artist artist) {
        this.artists.add(artist);
    }

    @Override
    public void add(Track track) {
        this.tracks.add(track);

        for (String trackMatch : track.matches()) {
            this.trackMatches.put(trackMatch, track);
        }
    }

    @Override
    public void load(List<Path> albumFiles, List<Path> artistFiles) {
        MusicMetadataLoader.load(this, albumFiles, artistFiles);
    }

    @Override
    public Optional<Artist> getArtist(ResourcePath path) {
        for (Artist artist : this.artists) {
            if (artist.path().equals(path)) {
                return Optional.of(artist);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Track> matchTrack(ResourcePath soundPath) {
        String[] splitPath = soundPath.representation().split("/");
        String filename = splitPath[splitPath.length - 1];

        return Optional.ofNullable(trackMatches.get(filename));
    }

}
