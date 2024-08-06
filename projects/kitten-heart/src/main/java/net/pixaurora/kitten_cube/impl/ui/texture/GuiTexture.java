package net.pixaurora.kitten_cube.impl.ui.texture;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_heart.impl.KitTunes;

public interface GuiTexture extends AbstractTexture {
    public static GuiTexture of(ResourcePath path, Size size) {
        return new TextureImpl(KitTunes.UI_LAYER.convertToGuiAsset(path), size);
    }
}
