package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.task;

import java.io.IOException;
import java.nio.file.Files;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import net.pixaurora.kit_tunes.build_logic.ProjectPaths;

public abstract class CleanModJsonTask extends DefaultTask {
    {
        var project = this.getProject();

        this.getOutput().set(ProjectPaths.modJsonDestination(project));
    }

    @OutputFile
    public abstract RegularFileProperty getOutput();

    @TaskAction
    public void run() {
        var modJsonDestination = this.getOutput().get().getAsFile().toPath();

        try {
            Files.deleteIfExists(modJsonDestination);
        } catch (IOException e) {
            throw new RuntimeException("Failed to clean mod json!", e);
        }
    }
}
