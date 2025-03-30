package net.pixaurora.kit_tunes.build_logic.music_asset_index_gen.task;

import java.io.IOException;

import javax.inject.Inject;

import net.pixaurora.kit_tunes.build_logic.ProjectPaths;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import net.pixaurora.kit_tunes.build_logic.Serialization;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.ModResourcesIO;
import net.pixaurora.kit_tunes.build_logic.music_asset_index_gen.MusicAssetIndex;
import net.pixaurora.kit_tunes.build_logic.music_asset_index_gen.extension.MusicAssetIndexExtension;

public abstract class CreateMusicAssetIndexJsonTask extends DefaultTask {
    private final MusicAssetIndexExtension configuration;

    {
        var project = this.getProject();

        this.getOutput().set(ProjectPaths.musicAssetJsonDestination(project));
    }

    @Inject
    public CreateMusicAssetIndexJsonTask(MusicAssetIndexExtension configuration) {
        this.configuration = configuration;
    }

    @Input
    public MusicAssetIndexExtension getConfiguration() {
        return this.configuration;
    }

    @OutputFile
    public abstract RegularFileProperty getOutput();

    @TaskAction
    public void run() {
        var serializer = Serialization.getSerializer();

        try {
            var musicAssetIndex = MusicAssetIndex.getFor(this.configuration.getMinecraft().get(), serializer);

            ModResourcesIO.writeJson(this.getOutput().get().getAsFile().toPath(), serializer.toJsonTree(musicAssetIndex));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to generate music_asset_index.json!", e);
        }
    }
}
