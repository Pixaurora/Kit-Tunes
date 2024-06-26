package net.pixaurora.kit_tunes.impl.music;

import java.util.List;
import java.util.Optional;

import com.google.gson.annotations.SerializedName;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.MusicMetadata;
import net.pixaurora.kit_tunes.impl.resource.ResourcePathImpl;

public class TrackImpl implements Track {
    private final List<String> matches;

    private final String name;
    private final Artist artist;
    private final Optional<Album> album;

    public TrackImpl(List<String> matches, String name, Artist artist, Optional<Album> album) {
        this.matches = matches;
        this.name = name;
        this.artist = artist;
        this.album = album;
    }

    @Override
    public List<String> matches() {
        return this.matches;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Artist artist() {
        return this.artist;
    }

    @Override
    public Optional<Album> album() {
        return this.album;
    }

    public class Data {
        private final List<String> matches;
        private final String name;

        @SerializedName("artist")
        private final String artistPath;

        public Data(List<String> validMatches, String defaultTitle, String artistPath) {
            this.matches = validMatches;
            this.name = defaultTitle;
            this.artistPath = artistPath;
        }

        public TrackImpl transformToTrack(Optional<Album> album) {
            ResourcePath artistPath = ResourcePathImpl.fromString(this.artistPath, "\\.", "\\.");

            return new TrackImpl(this.matches, this.name, MusicMetadata.findArtist(artistPath), album);
        }
    }

}
