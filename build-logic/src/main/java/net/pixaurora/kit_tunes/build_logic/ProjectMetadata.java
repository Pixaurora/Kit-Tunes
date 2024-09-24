package net.pixaurora.kit_tunes.build_logic;

import org.gradle.api.Project;

public record ProjectMetadata(Project project, ProjectProperties properties) {
    public ProjectMetadata(Project project) {
        this(project, new ProjectProperties(project));
    }

    public String modId() {
        String modId = properties.requireString(Property.MOD_ID);

        return modId;
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
        var project = this.properties.project();
        return project.getRootProject().equals(project);
    }
}
