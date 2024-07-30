package net.pixaurora.kit_tunes.build_logic.mod_resources_gen;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModResourcesExtension;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.task.CleanModIconTask;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.task.CopyModIconTask;

public class ModResourcesPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        var modResources = target.getExtensions().create("mod", ModResourcesExtension.class);

        var modIcon = modResources.getModIcon();

        var copyModIcon = target.getTasks().register("copyModIcon", CopyModIconTask.class, modIcon);

        var cleanModIcon = target.getTasks().register("cleanModIcon", CleanModIconTask.class, modIcon);

        target.getTasks().named("build").configure(task -> task.dependsOn(copyModIcon));
        target.getTasks().named("clean").configure(task -> task.dependsOn(cleanModIcon));
    }
}
