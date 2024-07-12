package net.pixaurora.kit_tunes.build_logic;

import java.util.Optional;

import org.gradle.api.Project;

public class ProjectMetadata {
	private final Project project;
    private final ProjectProperties properties;

    public ProjectMetadata(Project project) {
		this.project = project;
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
        String base = this.modId().replace("_", "-");

        if (this.isRootProject()) {
            base += "-" + this.properties.requireString(Property.UPDATE_TITLE);
        }

        if (!this.project.hasProperty(Property.MINECRAFT_VERSION_MIN.key())) {
            return base;
        } else {
            return base + "-minecraft-" + this.properties.requireString(Property.MINECRAFT_VERSION_MIN);
        }
    }

    private boolean isRootProject() {
        return this.project.getRootProject().equals(this.project);
    }
}
