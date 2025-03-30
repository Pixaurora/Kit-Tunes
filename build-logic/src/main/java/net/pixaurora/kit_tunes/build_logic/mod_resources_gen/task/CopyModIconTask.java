package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.task;

import java.io.IOException;
import java.nio.file.Files;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.ModResourcesIO;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.ModIcon;

public abstract class CopyModIconTask extends DefaultTask {
    {
        var project = this.getProject();

        this.getInputFile().set(this.getModIcon().map(icon -> icon.inputFor(project)));
        this.getOutput().set(this.getModIcon().map(icon -> icon.destinationFor(project)));
    }

    @Inject
    public CopyModIconTask(Provider<ModIcon> modIcon) {
        this.getModIcon().value(modIcon);
    }

    @Input
    abstract Property<ModIcon> getModIcon();

    @InputFile
    public abstract RegularFileProperty getInputFile();

    @OutputFile
    public abstract RegularFileProperty getOutput();

    @TaskAction
    public void run() {
        var input = this.getInputFile().get().getAsFile().toPath();
        var destination = this.getOutput().get().getAsFile().toPath();

        try {
            ModResourcesIO.ensureHasDirectories(destination);
            Files.deleteIfExists(destination);
            Files.copy(input, destination);
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy icon to project!", e);
        }
    }
}
