package net.pixaurora.kit_tunes.build_logic;

import java.nio.file.Path;

import org.gradle.api.Project;

public class ProjectPaths {
    public static Path resourcesDir(Project project) {
        var projectDir = project.getProjectDir().toPath();

        return projectDir.resolve("src/main/resources");
    }

    public static Path sharedResourcesDir(Project project) {
        var rootDir = project.getRootDir().toPath();

        return rootDir.resolve("shared-resources");
    }

    public static Path baseModJsonLocation(Project project) {
        return sharedResourcesDir(project).resolve("base_quilt.mod.json");
    }

    public static Path modJsonDestination(Project project) {
        return resourcesDir(project).resolve("quilt.mod.json");
    }
}
