package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data;

import java.nio.file.Path;

import org.gradle.api.Project;

import net.pixaurora.kit_tunes.build_logic.ProjectPaths;

public enum ModIcon {
    KIT_TUNES(),
    KIT_TUNES_API(),
    KITTEN_HEART(),
    KITTEN_THOUGHTS(),
    KITTEN_SQUARE(),
    KITTEN_SOUNDS();

    private final String modId;

    private final Path relativeInput;
    private final Path relativeDestination;

    ModIcon() {
        this.modId = this.name().toLowerCase();

        this.relativeInput = Path.of("icons", this.modId + ".png");
        this.relativeDestination = Path.of("assets", this.modId, "textures", "icon.png");
    }

    public Path inputFor(Project project) {
        return ProjectPaths.sharedResourcesDir(project).resolve(this.relativeInput);
    }

    public Path destinationFor(Project project) {
        return ProjectPaths.resourcesDir(project).resolve(this.relativeDestination);
    }

    public Path relativeDestination() {
        return this.relativeDestination;
    }

    public static ModIcon fromModId(String modId) {
        return ModIcon.valueOf(modId.toUpperCase());
    }
}
