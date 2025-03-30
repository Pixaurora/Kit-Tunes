package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.ModIcon;
import org.gradle.api.tasks.Optional;

public abstract class ModMetadata {
    {
        this.getLibrary().set(false);
    }

    @Input
    public abstract Property<String> getName();

    @Input
    public abstract Property<String> getDescription();

    @Input
    public abstract Property<ModIcon> getModIcon();

    @Input
    @Optional
    public abstract Property<String> getParentModId();

    @Input
    public abstract Property<Boolean> getLibrary();

    public void library() {
        this.getLibrary().set(true);
    }
}
