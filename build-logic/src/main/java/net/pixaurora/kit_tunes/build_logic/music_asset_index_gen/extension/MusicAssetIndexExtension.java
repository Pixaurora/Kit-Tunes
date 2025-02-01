package net.pixaurora.kit_tunes.build_logic.music_asset_index_gen.extension;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

public abstract class MusicAssetIndexExtension {
    @Input
    public abstract Property<String> getMinecraft();
}
