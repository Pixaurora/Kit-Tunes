package net.pixaurora.kit_tunes.build_logic.music_asset_index_gen.task;

import java.io.IOException;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import net.pixaurora.kit_tunes.build_logic.ProjectPaths;
import net.pixaurora.kit_tunes.build_logic.Serialization;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.ModResourcesIO;
import net.pixaurora.kit_tunes.build_logic.music_asset_index_gen.MusicAssetIndex;
import net.pixaurora.kit_tunes.build_logic.music_asset_index_gen.extension.MusicAssetIndexExtension;

public class CreateMusicAssetIndexJsonTask extends DefaultTask {
    private final MusicAssetIndexExtension configuration;

    @Inject
    public CreateMusicAssetIndexJsonTask(MusicAssetIndexExtension configuration) {
        this.configuration = configuration;
    }

    @Input
    public MusicAssetIndexExtension getConfiguration() {
        return this.configuration;
    }

    @TaskAction
    public void run() {
        var project = this.getProject();
        var serializer = Serialization.getSerializer();

        var musicAssetJsonDestination = ProjectPaths.musicAssetJsonDestination(project);

        try {
            var musicAssetIndex = MusicAssetIndex.getFor(this.configuration.getMinecraft().get(), serializer);

            ModResourcesIO.writeJson(musicAssetJsonDestination, serializer.toJsonTree(musicAssetIndex));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to generate music_asset_index.json!", e);
        }
    }
}
