package net.pixaurora.kitten_heart.impl.music;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathImpl;
import net.pixaurora.kitten_heart.impl.resource.TransformsTo;
import net.pixaurora.kitten_heart.impl.util.Pair;

public class AlbumImpl implements Album {
    private final ResourcePath path;
    private final String name;
    private final Optional<ResourcePath> albumArtPath;

    private final List<Track> tracks;

    public AlbumImpl(ResourcePath path, String name, Optional<ResourcePath> albumArtPath, List<Track> tracks) {
        this.path = path;
        this.name = name;
        this.albumArtPath = albumArtPath;
        this.tracks = tracks;
    }

    @Override
    public ResourcePath path() {
        return this.path;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Optional<ResourcePath> albumArtPath() {
        return this.albumArtPath;
    }

    @Override
    public List<Track> tracks() {
        return this.tracks;
    }

    public static class FromData implements TransformsTo<Pair<Album, List<Track>>> {
        private String name;
        private ResourcePath albumArtPath;

        private List<TrackImpl.TransformsToTrack> tracks;

        public Pair<Album, List<Track>> transform(ResourcePath path) {
            List<Track> tracks = new ArrayList<>();
            List<Track> includedTracks = new ArrayList<>();

            String baseTrackPath = path.path().replace("album", "track") + "/";

            int trackIndex = 0;
            for (TrackImpl.TransformsToTrack trackData : this.tracks) {
                trackIndex += 1;

                ResourcePath defaultTrackPath = new ResourcePathImpl(path.namespace(), baseTrackPath + trackIndex);
                Track track = trackData.transform(defaultTrackPath);

                tracks.add(track);
                if (trackData instanceof TrackImpl.FromData) {
                    includedTracks.add(track);
                }
            }

            return Pair.of(new AlbumImpl(path, name, Optional.ofNullable(this.albumArtPath), tracks), includedTracks);
        }
    }
}
