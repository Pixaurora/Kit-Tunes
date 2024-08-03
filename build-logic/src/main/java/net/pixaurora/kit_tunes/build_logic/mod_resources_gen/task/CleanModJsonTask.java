package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.task;

import java.io.IOException;
import java.nio.file.Files;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import net.pixaurora.kit_tunes.build_logic.ProjectPaths;

public class CleanModJsonTask extends DefaultTask {
    @TaskAction
    public void run() {
        var project = this.getProject();

        var modJsonDestination = ProjectPaths.modJsonDestination(project);

        try {
            Files.deleteIfExists(modJsonDestination);
        } catch (IOException e) {
            throw new RuntimeException("Failed to clean mod json!", e);
        }
    }
}
