package net.pixaurora.kitten_heart.impl.config;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import net.pixaurora.kit_tunes.api.music.history.ListenRecord;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.api.scrobble.ScrobblerId;
import net.pixaurora.kitten_heart.impl.music.AlbumImpl;
import net.pixaurora.kitten_heart.impl.music.ArtistImpl;
import net.pixaurora.kitten_heart.impl.music.TrackImpl;
import net.pixaurora.kitten_heart.impl.music.history.ListenRecordSerializer;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathImpl;
import net.pixaurora.kitten_heart.impl.scrobble.ScrobblerIdSerializer;
import net.pixaurora.kitten_heart.impl.scrobble.scrobbler.Scrobbler;

public class Serialization {
    private static Gson SERIALIZER = createSerializer();

    private static final Gson createSerializer() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .registerTypeAdapter(ScrobblerCache.class, new ScrobblerCache.Serializer())
                .registerTypeAdapter(Scrobbler.class, Scrobbler.TYPES.itemSerialzier())
                .registerTypeAdapter(ResourcePath.class, ResourcePathImpl.SERIALIZER)
                .registerTypeAdapter(AlbumImpl.FromPath.class, AlbumImpl.FromPath.SERIALIZER)
                .registerTypeAdapter(ArtistImpl.FromPath.class, ArtistImpl.FromPath.SERIALIZER)
                .registerTypeAdapter(TrackImpl.TransformsToTrack.class, new TrackImpl.TransformsToTrack.Serializer())
                .registerTypeAdapter(TrackImpl.FromData.class, new TrackImpl.FromData.Serializer())
                .registerTypeAdapter(TrackImpl.FromPath.class, TrackImpl.FromPath.SERIALIZER)
                .registerTypeAdapter(ListenRecord.class, new ListenRecordSerializer())
                .registerTypeAdapter(ScrobblerId.class, new ScrobblerIdSerializer())
                .registerTypeAdapter(Duration.class, new DurationSerializer())
                .registerTypeAdapter(Instant.class, new InstantSerializer())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    public static class DurationSerializer implements DualSerializer<Duration> {
        @Override
        public JsonElement serialize(Duration src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getSeconds());
        }

        @Override
        public Duration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return Duration.ofSeconds(json.getAsLong());
        }

    }

    public static class InstantSerializer implements DualSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getEpochSecond());
        }

        @Override
        public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return Instant.ofEpochSecond(json.getAsLong());
        }

    }

    public static Gson serializer() {
        return SERIALIZER;
    }
}
