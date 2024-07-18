package net.pixaurora.kitten_cube.impl.ui.texture;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Size;

public class TextureImpl implements Texture, GuiTexture {
    private final ResourcePath path;
    private final Size size;

    public TextureImpl(ResourcePath path, Size size) {
        this.path = path;
        this.size = size;
    }

    public ResourcePath path() {
        return this.path;
    }

    public Size size() {
        return this.size;
    }
}
