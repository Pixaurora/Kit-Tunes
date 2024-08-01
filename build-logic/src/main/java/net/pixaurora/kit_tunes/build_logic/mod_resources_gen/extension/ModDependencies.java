package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension;

import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.ModDependency;

public abstract class ModDependencies {
    private final List<ModDependency> dependencies = new ArrayList<>();

    protected List<ModDependency> get() {
        return this.dependencies;
    }

    public void required(String modId, String versionString) {
        this.dependencies.add(new ModDependency(modId, versionString, false));
    }

    public void optional(String modId, String versionString) {
        this.dependencies.add(new ModDependency(modId, versionString, true));
    }
}
