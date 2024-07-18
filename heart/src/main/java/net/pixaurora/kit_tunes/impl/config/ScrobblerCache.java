package net.pixaurora.kit_tunes.impl.config;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.error.KitTunesException;
import net.pixaurora.kit_tunes.impl.scrobble.ScrobbleInfo;
import net.pixaurora.kit_tunes.impl.scrobble.Scrobbler;
import net.pixaurora.kit_tunes.impl.scrobble.SimpleScrobbler;

public class ScrobblerCache implements SimpleScrobbler {
    private List<Scrobbler> scrobblers;

    public ScrobblerCache(List<Scrobbler> scrobblers) {
        this.scrobblers = new ArrayList<>(scrobblers);
    }

    public List<Scrobbler> scrobblers() {
        return this.scrobblers;
    }

    public void addScrobbler(Scrobbler scrobbler) {
        this.scrobblers.add(scrobbler);
    }

    @Override
    public void startScrobbling(ScrobbleInfo track) {
        this.handleScrobbling(scrobbler -> scrobbler.startScrobbling(track));
    }

    @Override
    public void completeScrobbling(ScrobbleInfo track) {
        this.handleScrobbling(scrobbler -> scrobbler.completeScrobbling(track));
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

            for (JsonElement scrobblerData : json.getAsJsonArray().asList()) {
                Scrobbler scrobbler = context.deserialize(scrobblerData, Scrobbler.class);
                scrobblers.add(scrobbler);
            }

            return new ScrobblerCache(scrobblers);
        }

    }
}
