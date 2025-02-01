package net.pixaurora.kit_tunes.build_logic.music_asset_index_gen;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public enum MusicCategory {
    MAIN_MENU("minecraft/sounds/music/menu"),
    NETHER("minecraft/sounds/music/game/nether"),
    ENDER_DRAGON("minecraft/sounds/music/game/end/boss"),
    CREDITS("minecraft/sounds/music/game/end/alpha"),
    END("minecraft/sounds/music/game/end/the_end"),
    OVERWORLD("minecraft/sounds/music/game"),
    RECORDS("minecraft/sounds/records");

    private final String prefix;

    MusicCategory(String prefix) {
        this.prefix = prefix;
    }

    public boolean matches(String value) {
        return value.startsWith(prefix);
    }

    public static class Serializer implements JsonSerializer<MusicCategory> {
        @Override
        public JsonElement serialize(MusicCategory src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.name().toLowerCase());
        }
    }
}
