package net.pixaurora.kitten_heart.impl.config;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.pixaurora.catculator.api.http.Client;
import net.pixaurora.kit_tunes.api.music.history.ListenRecord;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.error.KitTunesException;
import net.pixaurora.kitten_heart.impl.scrobble.SimpleScrobbler;
import net.pixaurora.kitten_heart.impl.scrobble.scrobbler.Scrobbler;

public class ScrobblerCache implements SimpleScrobbler {
    private List<Scrobbler> scrobblers;

    public ScrobblerCache(List<Scrobbler> scrobblers) {
        this.scrobblers = new ArrayList<>(scrobblers);
    }

    public static ScrobblerCache defaults() {
        return new ScrobblerCache(new ArrayList<>());
    }

    public List<Scrobbler> scrobblers() {
        return this.scrobblers;
    }

    public void addScrobbler(Scrobbler scrobbler) {
        this.scrobblers.removeIf(item -> item.equals(scrobbler));
        this.scrobblers.add(scrobbler);
    }

    @Override
    public void startScrobbling(Client client, ListenRecord track) {
        this.handleScrobbling(scrobbler -> scrobbler.startScrobbling(client, track));
    }

    @Override
    public void completeScrobbling(Client client, ListenRecord track) {
        this.handleScrobbling(scrobbler -> {
            scrobbler.completeScrobbling(client, track);
            track.succeededFor(scrobbler.id());
        });
    }

    private void handleScrobbling(ScrobbleAction action) {
        for (Scrobbler scrobbler : this.scrobblers) {
            try {
                action.doFor(scrobbler);
            } catch (Exception e) {
                KitTunes.LOGGER.error("Ignoring exception encountered while scrobbling.", e);
            }
        }
    }

    private static interface ScrobbleAction {
        public void doFor(Scrobbler scrobbler) throws KitTunesException;
    }

    public static class Serializer implements DualSerializer<ScrobblerCache> {
        @Override
        public JsonElement serialize(ScrobblerCache item, Type _type, JsonSerializationContext context) {
            JsonArray scrobblers = new JsonArray();

            for (Scrobbler scrobbler : item.scrobblers) {
                JsonObject scrobblerData = context.serialize(scrobbler, Scrobbler.class).getAsJsonObject();

                scrobblers.add(scrobblerData);
            }

            return scrobblers;
        }

        @Override
        public ScrobblerCache deserialize(JsonElement json, Type _type, JsonDeserializationContext context)
                throws JsonParseException {
            ArrayList<Scrobbler> scrobblers = new ArrayList<>();

            for (JsonElement scrobblerData : json.getAsJsonArray()) {
                Scrobbler scrobbler = context.deserialize(scrobblerData, Scrobbler.class);
                scrobblers.add(scrobbler);
            }

            return new ScrobblerCache(scrobblers);
        }

    }
}
