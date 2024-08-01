package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.ModIcon;

public abstract class ModMetadata {
    private boolean isLibrary = false;

    @Input
    public abstract Property<String> getName();

    @Input
    public abstract Property<String> getDescription();

    @Input
    public abstract Property<ModIcon> getModIcon();

    public ModIcon iconFromModId(String modId) {
        return ModIcon.fromModId(modId);
    }

    @Input
    public abstract Property<String> getParentModId();

    public boolean isLibrary() {
        return this.isLibrary;
    }

    public void library() {
        this.isLibrary = true;
    }
}
