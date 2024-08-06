package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension;

import javax.inject.Inject;

import org.gradle.api.Project;
import org.gradle.api.provider.Provider;

import net.pixaurora.kit_tunes.build_logic.ProjectMetadata;
import net.pixaurora.kit_tunes.build_logic.ProjectProperties;
import net.pixaurora.kit_tunes.build_logic.Util;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.ModIcon;

public abstract class ModResourcesExtension extends ModInfoExtension {
    private final ProjectProperties propertiesWorkaround;

    @Inject
    public ModResourcesExtension(Project project) {
        this.propertiesWorkaround = new ProjectProperties(project);
    }

    public void modIdFromProperties() {
        var autoMetadata = new ProjectMetadata(this.propertiesWorkaround.project());

        this.getId().set(autoMetadata.modId());
    }

    public String workaroundProperty(String propertyKey) {
        return propertiesWorkaround.requireString(propertyKey);
    }

    public Provider<String> nameFromModId() {
        return this.getId().map(id -> Util.titleCase(id.replace("_", " ")));
    }

    public Provider<ModIcon> iconFromModId() {
        return this.getId().map(ModIcon::fromModId);
    }
}
