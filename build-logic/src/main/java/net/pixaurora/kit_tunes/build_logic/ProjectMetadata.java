package net.pixaurora.kit_tunes.build_logic;

import java.util.Optional;

import org.gradle.api.Project;

public class ProjectMetadata {
    private final ProjectProperties properties;

    public ProjectMetadata(Project project) {
        this.properties = new ProjectProperties(project);
    }

    public String mod_id() {
        String modId = String.valueOf(properties.requireString(Property.BASE_MOD_ID));

        Optional<String> subModId = properties.optionalString(Property.SUB_MOD_ID);

        if (subModId.isPresent()) {
            modId = modId + "_" + subModId.get();
        }

        return modId;
    }

    public String base_file_name() {
        String modId = this.mod_id().replace("_", "-");

        return modId + "-" + this.properties.requireString(Property.MOD_VERSION);
    }

    public String game_mod_file_name() {
        return this.base_file_name() + "+minecraft-" + this.properties.requireString(Property.MINECRAFT_VERSION);
    }
}
