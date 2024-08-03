package net.pixaurora.kit_tunes.build_logic.mod_resources_gen;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModResourcesExtension;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.task.CleanModIconTask;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.task.CleanModJsonTask;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.task.CopyModIconTask;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.task.CreateModJsonTask;

public class ModResourcesPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        var modResources = target.getExtensions().create("mod", ModResourcesExtension.class, target);

        var modIcon = modResources.getMetadata().getModIcon();

        var tasks = target.getTasks();

        var generateResources = tasks.create("generateResources");
        var cleanResources = tasks.create("cleanResources");

        tasks.named("processResources").configure(task -> task.dependsOn(generateResources));
        tasks.named("clean").configure(task -> task.dependsOn(cleanResources));

        var copyModIcon = tasks.register("copyModIcon", CopyModIconTask.class, modIcon);
        generateResources.dependsOn(copyModIcon);
        var cleanModIcon = tasks.register("cleanModIcon", CleanModIconTask.class, modIcon);
        cleanResources.dependsOn(cleanModIcon);

        var createModJson = tasks.register("createModJson", CreateModJsonTask.class, modResources);
        generateResources.dependsOn(createModJson);
        var cleanModJson = tasks.register("cleanModJson", CleanModJsonTask.class);
        cleanResources.dependsOn(cleanModJson);
    }
}
