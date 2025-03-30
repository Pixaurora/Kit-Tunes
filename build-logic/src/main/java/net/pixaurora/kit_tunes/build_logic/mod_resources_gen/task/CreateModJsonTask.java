package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.task;

import java.io.IOException;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.*;

import net.pixaurora.kit_tunes.build_logic.ProjectPaths;
import net.pixaurora.kit_tunes.build_logic.Serialization;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.ModResourcesIO;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModInfoExtension;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModResourcesExtension;

public abstract class CreateModJsonTask extends DefaultTask {
    public RegularFileProperty baseModJsonLocation = this.getProject().getObjects().fileProperty().value(ProjectPaths.baseModJsonLocation(this.getProject()));
    public RegularFileProperty output = this.getProject().getObjects().fileProperty().value(ProjectPaths.modJsonDestination(this.getProject()));

    private final ModResourcesExtension configuration;

    @Inject
    public CreateModJsonTask(ModResourcesExtension configuration) {
        this.configuration = configuration;
    }

    @Nested
    public ModResourcesExtension getConfiguration() {
        return this.configuration;
    }

    @InputFile
    public RegularFileProperty getBaseModJsonLocation() {
        return this.baseModJsonLocation;
    }

    @OutputFile
    public RegularFileProperty getOutput() {
        return this.output;
    }

    @TaskAction
    public void run() {
        var serializer = Serialization.getSerializer();

        var baseJsonLocation = this.getBaseModJsonLocation().get().getAsFile().toPath();
        var modJsonDestination = this.getOutput().get().getAsFile().toPath();

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
