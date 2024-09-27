package net.pixaurora.kitten_heart.impl.service;

import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public interface MusicMetadataService {
    public void load(List<Path> albumFiles, List<Path> artistFiles, List<Path> trackFiles);

    public Optional<Artist> getArtist(ResourcePath path);

    public Optional<Track> getTrack(ResourcePath path);

    public Optional<Track> matchTrack(ResourcePath soundPath);

    public List<Album> albumsWithTrack(Track track);

    public Duration trackDuration(Track track);
}
