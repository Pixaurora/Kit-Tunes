package net.pixaurora.kit_tunes.impl.ui.texture;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.resource.ResourcePathImpl;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public interface Texture extends AbstractTexture {
    public static Texture of(String path, Size size) {
        return of(ResourcePathImpl.fromString(path), size);
    }

    public static Texture of(ResourcePath path, Size size) {
        return new TextureImpl(KitTunes.UI_LAYER.convertToRegularAsset(path), size);
    }
}
