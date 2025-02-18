package net.pixaurora.kitten_heart.impl.music.history;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.music.history.ListenRecord;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.config.DualSerializer;
import net.pixaurora.kitten_heart.impl.music.AlbumImpl;
import net.pixaurora.kitten_heart.impl.music.TrackImpl;

public class ListenRecordSerializer implements DualSerializer<ListenRecord> {
    @Override
    public JsonElement serialize(ListenRecord record, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        json.add("track", context.serialize(record.track().path(), ResourcePath.class));

        if (record.album().isPresent()) {
            json.add("album", context.serialize(record.album().get().path(), ResourcePath.class));
        }

        json.add("timestamp", context.serialize(record.timestamp()));

        json.add("progress", context.serialize(record.durations().progress()));
        json.add("duration", context.serialize(record.durations().full()));

        json.add("succeeded", context.serialize(record.succeededScrobblers()));

        return json;
    }

    @Override
    public ListenRecord deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        TrackImpl.FromPath trackFromPath = context.deserialize(object.get("track"), TrackImpl.FromPath.class);
        Track track = trackFromPath.transform();

        Optional<AlbumImpl.FromPath> albumFromPath = Optional.ofNullable(object.get("album"))
                .map(album0 -> context.deserialize(album0, AlbumImpl.FromPath.class));
        Optional<Album> album = albumFromPath.map(AlbumImpl.FromPath::transform);

        Instant timestamp = context.deserialize(object.get("timestamp"), Instant.class);

        Duration progress = context.deserialize(object.get("duration"), Duration.class);
        Duration duration = context.deserialize(object.get("progress"), Duration.class);

        List<String> succeededScrobblers = new ArrayList<>();

        for (JsonElement scrobblerId : object.get("succeeded").getAsJsonArray()) {
            if (scrobblerId.isJsonPrimitive()) {
                succeededScrobblers.add(scrobblerId.getAsString());
            } else {
                JsonObject scrobbler = scrobblerId.getAsJsonObject();

                String username = scrobbler.get("username").getAsString();
                String scrobblerType = scrobbler.get("scrobbler_type").getAsString();

                succeededScrobblers.add(scrobblerType + ":" + username);
            }
        }

        return new ListenRecord(track, album, timestamp, new ImmutableListenDurations(progress, duration),
                succeededScrobblers);
    }
}
