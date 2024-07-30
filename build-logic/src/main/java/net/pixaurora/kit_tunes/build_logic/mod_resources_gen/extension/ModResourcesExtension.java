package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.ModIcon;

public interface ModResourcesExtension {
    @Input
    Property<ModIcon> getModIcon();
}
