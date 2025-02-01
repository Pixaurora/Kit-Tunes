package net.pixaurora.kit_tunes.build_logic.music_asset_index_gen.task;

import java.io.IOException;
import java.nio.file.Files;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import net.pixaurora.kit_tunes.build_logic.ProjectPaths;

public class CleanMusicAssetIndexJsonTask extends DefaultTask {
    @TaskAction
    public void run() {
        var project = this.getProject();

        var musicAssetJsonDestination = ProjectPaths.musicAssetJsonDestination(project);

        try {
            Files.deleteIfExists(musicAssetJsonDestination);
        } catch (IOException e) {
            throw new RuntimeException("Failed to clean mod json!", e);
        }
    }
}
