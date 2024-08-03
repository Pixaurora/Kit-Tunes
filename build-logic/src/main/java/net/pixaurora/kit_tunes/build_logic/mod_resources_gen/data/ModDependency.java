package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface ModDependency {
    public String modId();

    public JsonElement version();

    public boolean isOptional();

    public default JsonElement toJson() {
        var dependencyObject = new JsonObject();

        dependencyObject.addProperty("id", this.modId());
        dependencyObject.add("versions", this.version());

        if (this.isOptional()) {
            dependencyObject.addProperty("optional", true);
        }

        return dependencyObject;
    }

    public static interface Mutable {
        public void version(String exactVersion);

        public void version(String minVersion, String maxVersion);

        public void versionAbove(String minVersion);
    }
}
