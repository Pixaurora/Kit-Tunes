package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data;

import java.nio.file.Path;

import org.gradle.api.Project;

import net.pixaurora.kit_tunes.build_logic.ProjectPaths;
import org.gradle.api.file.RegularFile;

public enum ModIcon {
    KIT_TUNES(),
    KIT_TUNES_API(),
    KITTEN_HEART(),
    CATCULATOR(),
    KITTEN_SQUARE(),
    KITTEN_SOUNDS();

    private final String modId;

    ModIcon() {
        this.modId = this.name().toLowerCase();
    }

    public RegularFile inputFor(Project project) {
        return ProjectPaths.sharedResourcesDir(project).dir("icons").file(this.modId + ".png");
    }

    public RegularFile destinationFor(Project project) {
        return ProjectPaths.resourcesDir(project)
                .dir("assets")
                .dir(this.modId)
                .dir("textures")
                .file("icon.png");
    }

    public String relativeDestination() {
        return "assets/" + this.modId + "/textures/icon.png";
    }

    public static ModIcon fromModId(String modId) {
        return ModIcon.valueOf(modId.toUpperCase());
    }
}
