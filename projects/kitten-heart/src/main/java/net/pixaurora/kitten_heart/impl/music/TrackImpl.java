package net.pixaurora.kitten_heart.impl.music;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.config.DualSerializer;
import net.pixaurora.kitten_heart.impl.config.DualSerializerFromString;
import net.pixaurora.kitten_heart.impl.music.metadata.MusicMetadata;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathImpl;
import net.pixaurora.kitten_heart.impl.resource.TransformsTo;

public class TrackImpl implements Track {
    private final ResourcePath path;

    private final List<String> matches;

    private final String name;
    private final Artist artist;

    public TrackImpl(ResourcePath path, List<String> matches, String name, Artist artist) {
        this.path = path;
        this.matches = matches;
        this.name = name;
        this.artist = artist;
    }

    @Override
    public ResourcePath path() {
        return this.path;
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
    public List<Album> albums() {
        return MusicMetadata.albumsWithTrack(this);
    }

    public static interface TransformsToTrack extends TransformsTo<Track> {
        public static class Serializer implements JsonDeserializer<TrackImpl.TransformsToTrack> {
            @Override
            public TransformsToTrack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    return context.deserialize(json, FromPath.class);
                } catch (Throwable e) {
                    return context.deserialize(json, FromData.class);
                }
            }
        }
    }

    public static class FromData implements TransformsToTrack {
        private final List<String> matches;
        private final String name;

        private final ArtistImpl.FromPath artist;

        public FromData(List<String> matches, String defaultTitle, ArtistImpl.FromPath artist) {
            this.matches = matches;
            this.name = defaultTitle;
            this.artist = artist;
        }

        @Override
        public TrackImpl transform(ResourcePath path) {
            return new TrackImpl(path, this.matches, this.name, artist.transform());
        }

        public static class Serializer implements JsonDeserializer<TrackImpl.FromData> {
            @Override
            public FromData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                JsonObject trackObject = json.getAsJsonObject();

                JsonElement matchesElement = trackObject.get("matches");
                List<String> matches = matchesElement.isJsonArray() ? matchesFromArray(matchesElement.getAsJsonArray())
                        : matchesFromString(matchesElement.getAsJsonPrimitive());

                String name = trackObject.getAsJsonPrimitive("name").getAsString();

                ArtistImpl.FromPath artist = context.deserialize(trackObject.get("artist"), ArtistImpl.FromPath.class);

                return new TrackImpl.FromData(matches, name, artist);
            }

            public List<String> matchesFromArray(JsonArray matchesArray) {
                List<String> matches = new ArrayList<>();

                for (JsonElement element : matchesArray) {
                    matches.add(element.getAsJsonPrimitive().getAsString());
                }

                return matches;
            }

            public List<String> matchesFromString(JsonPrimitive match) {
                return Arrays.asList(match.getAsString());
            }
        }
    }

    public static class FromPath implements TransformsToTrack {
        public static final DualSerializer<FromPath> SERIALIZER = new DualSerializerFromString<>(
                track -> track.path.representation(),
                representation -> new FromPath(ResourcePathImpl.fromString(representation)));

        private final ResourcePath path;

        public FromPath(ResourcePath path) {
            this.path = path;
        }

        @Override
        public Track transform(ResourcePath path) {
            return transform();
        }

        public Track transform() {
            Optional<Track> track = MusicMetadata.getTrack(this.path);

            if (track.isPresent()) {
                return track.get();
            } else {
                throw new RuntimeException("No Track found with path `" + path.representation() + "`!");
            }
        }
    }
}
