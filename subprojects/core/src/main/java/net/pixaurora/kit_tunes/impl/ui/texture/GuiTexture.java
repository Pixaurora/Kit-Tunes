package net.pixaurora.kit_tunes.impl.ui.texture;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public interface GuiTexture extends Texture {
    public static GuiTexture of(ResourcePath path, Size size) {
        return new TextureImpl(KitTunes.UI_LAYER.convertToGuiAsset(path), size);
    }
}
