package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.version;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public record VersionRange(String minVersion, String maxVersion) implements Version {
    @Override
    public JsonElement toJson() {
        JsonObject versions = new JsonObject();

        JsonArray all = new JsonArray();
        all.add(">=" + minVersion);
        all.add("<=" + maxVersion);

        versions.add("all", all);

        return versions;
    }
}
