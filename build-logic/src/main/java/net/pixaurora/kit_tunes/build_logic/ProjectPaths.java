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
}
