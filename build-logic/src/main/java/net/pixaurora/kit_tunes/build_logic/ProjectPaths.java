package net.pixaurora.kit_tunes.build_logic;

import java.nio.file.Path;

import org.gradle.api.Project;
import org.gradle.api.file.Directory;
import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;

public class ProjectPaths {
    public static Directory resourcesDir(Project project) {
        var projectDir = project.getLayout().getProjectDirectory();

        return projectDir.dir("src").dir("main").dir("resources");
    }

    public static Directory sharedResourcesDir(Project project) {
        var rootDir = project.getRootProject().getLayout().getProjectDirectory();

        return rootDir.dir("shared-resources");
    }

    public static RegularFile baseModJsonLocation(Project project) {
        return sharedResourcesDir(project).file("base_quilt.mod.json");
    }

    public static RegularFile modJsonDestination(Project project) {
        return resourcesDir(project).file("quilt.mod.json");
    }

    public static RegularFile musicAssetJsonDestination(Project project) {
        return resourcesDir(project).file("music_asset_index.json");
    }
}
