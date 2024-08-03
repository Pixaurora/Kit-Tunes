package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.task;

import java.io.IOException;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import net.pixaurora.kit_tunes.build_logic.ProjectPaths;
import net.pixaurora.kit_tunes.build_logic.Serialization;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.ModResourcesIO;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModInfoExtension;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModResourcesExtension;

public abstract class CreateModJsonTask extends DefaultTask {
    private final ModResourcesExtension configuration;

    @Inject
    public CreateModJsonTask(ModResourcesExtension configuration) {
        this.configuration = configuration;
    }

    @Input
    public ModResourcesExtension getConfiguration() {
        return this.configuration;
    }

    @TaskAction
    public void run() {
        var project = this.getProject();
        var serializer = Serialization.getSerializer();

        var baseJsonLocation = ProjectPaths.baseModJsonLocation(project);
        var modJsonDestination = ProjectPaths.modJsonDestination(project);

        try {
            var baseJson = ModResourcesIO.loadJson(baseJsonLocation);
            var generatedJson = serializer.toJsonTree(this.configuration, ModInfoExtension.class);

            var mergedJson = Serialization.merge(baseJson, generatedJson);

            ModResourcesIO.writeJson(modJsonDestination, mergedJson);
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate quilt.mod.json!", e);
        }
    }
}
