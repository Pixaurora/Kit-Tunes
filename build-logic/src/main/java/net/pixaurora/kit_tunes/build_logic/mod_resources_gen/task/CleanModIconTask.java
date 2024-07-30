package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.task;

import java.io.IOException;
import java.nio.file.Files;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.ModIcon;

public abstract class CleanModIconTask extends DefaultTask {
    @Inject
    public CleanModIconTask(Provider<ModIcon> modIcon) {
        this.getModIcon().value(modIcon);
    }

    @Input
    abstract Property<ModIcon> getModIcon();

    @TaskAction
    public void run() {
        var project = this.getProject();

        var icon = this.getModIcon().get();
        var destination = icon.destinationFor(project);

        try {
            Files.deleteIfExists(destination);
        } catch (IOException e) {
            throw new RuntimeException("Failed to clean mod icon!", e);
        }
    }
}
