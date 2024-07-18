package net.pixaurora.kit_tunes.build_logic;

import java.util.Optional;

import org.gradle.api.Project;

public class ProjectMetadata {
    private final ProjectProperties properties;

    public ProjectMetadata(Project project) {
        this.properties = new ProjectProperties(project);
    }

    public String modId() {
        String modId = String.valueOf(properties.requireString(Property.BASE_MOD_ID));
        Optional<String> subModId = properties.optionalString(Property.SUB_MOD_ID);

        if (subModId.isPresent()) {
            return "kitten" + "_" + subModId.get();
        } else {
            return modId;
        }
    }

    public String version() {
        return this.properties.requireString(Property.MOD_VERSION);
    }

    public String archiveName() {
        String base = this.modId().replace("_", "-");

        if (this.isMainProject()) {
            base += "-" + this.properties.requireString(Property.UPDATE_TITLE);
        }

        if (!this.properties.has(Property.MINECRAFT_VERSION_MIN)) {
            return base;
        } else {
            return base + "-minecraft-" + this.properties.requireString(Property.MINECRAFT_VERSION_MIN);
        }
    }

    private boolean isMainProject() {
        return this.properties.has(Property.IS_MAIN_PROJECT);
    }
}
