package net.pixaurora.kit_tunes.build_logic;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.ModJsonSerializer;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModInfoExtension;

public class Serialization {
    private static final Gson SERIALIZER = new GsonBuilder()
            .registerTypeAdapter(ModInfoExtension.class, new ModJsonSerializer())
            .setPrettyPrinting().create();

    public static Gson getSerializer() {
        return SERIALIZER;
    }

    private static JsonArray mergeArrays(JsonArray base, JsonArray prioritized) {
        var outputArray = new JsonArray();

        for (var array : List.of(base, prioritized)) {
            for (var item : array) {
                outputArray.add(item);
            }
        }

        return outputArray;
    }

    private static JsonObject mergeObjects(JsonObject base, JsonObject prioritized) {
        var outputObject = new JsonObject();

        for (var entry : base.entrySet()) {
            var field = entry.getKey();

            JsonElement value;
            if (prioritized.has(field)) {
                value = Serialization.merge(entry.getValue(), prioritized.get(field));
            } else {
                value = entry.getValue();
            }

            outputObject.add(field, value);
        }

        for (var entry : prioritized.entrySet()) {
            var field = entry.getKey();

            if (!outputObject.has(field)) {
                outputObject.add(field, entry.getValue());
            }
        }

        return outputObject;
    }

    public static JsonElement merge(JsonElement base, JsonElement prioritized) {
        if (base.isJsonArray() && prioritized.isJsonArray()) {
            return mergeArrays(base.getAsJsonArray(), prioritized.getAsJsonArray());
        } else if (base.isJsonObject() && prioritized.isJsonObject()) {
            return mergeObjects(base.getAsJsonObject(), prioritized.getAsJsonObject());
        } else { // Is a simple value type like null or a primitive, or are different types
            return prioritized;
        }
    }
}
