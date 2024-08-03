package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.version;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public interface Version {
    public JsonElement toJson();

    public static interface Simple extends Version {
        public String versionString();

        @Override
        public default JsonElement toJson() {
            return new JsonPrimitive(this.versionString());
        }
    }
}
