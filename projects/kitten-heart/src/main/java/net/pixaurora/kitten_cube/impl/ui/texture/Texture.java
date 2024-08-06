package net.pixaurora.kitten_cube.impl.ui.texture;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathImpl;

public interface Texture extends AbstractTexture {
    public static Texture of(String path, Size size) {
        return of(ResourcePathImpl.fromString(path), size);
    }

    public static Texture of(ResourcePath path, Size size) {
        return new TextureImpl(KitTunes.UI_LAYER.convertToRegularAsset(path), size);
    }
}
