package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entrypoints {
    private final Map<String, List<String>> entrypoints = new HashMap<>();

    protected Map<String, List<String>> get() {
        return this.entrypoints;
    }

    public void single(String modId, String classPath) {
        this.entrypoints.computeIfAbsent(modId, path -> new ArrayList<>()).add(classPath);
    }
}
