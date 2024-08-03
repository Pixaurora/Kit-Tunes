package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data;

import com.google.gson.JsonElement;

import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.version.AboveVersion;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.version.AnyVersion;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.version.ExactVersion;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.version.Version;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.version.VersionRange;

public class MutableDependency implements ModDependency, ModDependency.Mutable {
    private final String modId;
    private final boolean isOptional;

    private Version version;

    public MutableDependency(String modId, boolean isOptional) {
        this.modId = modId;
        this.isOptional = isOptional;
        this.version = AnyVersion.INSTANCE;
    }

    @Override
    public String modId() {
        return this.modId;
    }

    @Override
    public boolean isOptional() {
        return this.isOptional;
    }

    @Override
    public JsonElement version() {
        return this.version.toJson();
    }

    @Override
    public void version(String exactVersion) {
        this.version = new ExactVersion(exactVersion);
    }

    @Override
    public void version(String minVersion, String maxVersion) {
        if (minVersion.equals(maxVersion)) {
            this.version(minVersion);
            return;
        }

        this.version = new VersionRange(minVersion, maxVersion);
    }

    @Override
    public void versionAbove(String minVersion) {
        this.version = new AboveVersion(minVersion);
    }
}
