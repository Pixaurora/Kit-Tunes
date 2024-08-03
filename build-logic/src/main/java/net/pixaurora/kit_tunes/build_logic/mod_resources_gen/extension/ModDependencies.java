package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension;

import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.ModDependency;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.MutableDependency;

public abstract class ModDependencies {
    private final List<ModDependency> dependencies = new ArrayList<>();

    public List<ModDependency> get() {
        return this.dependencies;
    }

    public ModDependency.Mutable required(String modId) {
        var dependency = new MutableDependency(modId, false);
        this.dependencies.add(dependency);

        return dependency;
    }

    public ModDependency.Mutable optional(String modId) {
        var dependency = new MutableDependency(modId, true);
        this.dependencies.add(dependency);

        return dependency;
    }
}
