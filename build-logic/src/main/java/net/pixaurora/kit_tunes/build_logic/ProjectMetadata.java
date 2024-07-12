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
            modId = modId + "_" + subModId.get();
        }

        return modId;
    }

	public String version() {
		return this.properties.requireString(Property.MOD_VERSION);
	}

    public String archiveName() {
        return this.modId().replace("_", "-") + "-" + this.properties.requireString(Property.UPDATE_TITLE);
    }

    public String gameModVersion() {
        return this.version() + "+minecraft-" + this.properties.requireString(Property.MINECRAFT_VERSION);
    }
}
