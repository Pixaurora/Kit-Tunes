package net.pixaurora.kit_tunes.build_logic.music_asset_index_gen;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.UnknownTaskException;

import net.pixaurora.kit_tunes.build_logic.music_asset_index_gen.extension.MusicAssetIndexExtension;
import net.pixaurora.kit_tunes.build_logic.music_asset_index_gen.task.CleanMusicAssetIndexJsonTask;
import net.pixaurora.kit_tunes.build_logic.music_asset_index_gen.task.CreateMusicAssetIndexJsonTask;

public class MusicAssetIndexPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        var musicAssetIndexConfig = target.getExtensions().create("music_assets", MusicAssetIndexExtension.class);

        var tasks = target.getTasks();

        var createMusicAssetIndexJson = tasks.register("createMusicAssetIndexJson", CreateMusicAssetIndexJsonTask.class,
                musicAssetIndexConfig);
        var cleanMusicAssetIndexJson = tasks.register("cleanMusicAssetIndexJson", CleanMusicAssetIndexJsonTask.class);

        tasks.named("processResources").configure(task -> task.dependsOn(createMusicAssetIndexJson));
        try {
            tasks.named("genSources").configure(task -> task.dependsOn(createMusicAssetIndexJson));
        } catch (UnknownTaskException e) {
        }

        tasks.named("clean").configure(task -> task.dependsOn(cleanMusicAssetIndexJson));
    }
}
