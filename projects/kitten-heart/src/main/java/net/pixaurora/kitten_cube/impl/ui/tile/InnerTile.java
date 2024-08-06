package net.pixaurora.kitten_cube.impl.ui.tile;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;

public class InnerTile {
    private final GuiTexture texture;
    private final Point textureOffset;
    private final Size size;

    public InnerTile(GuiTexture texture, Point textureOffset, Size size) {
        this.texture = texture;
        this.textureOffset = textureOffset;
        this.size = size;
    }

    public GuiTexture texture() {
        return this.texture;
    }

    public Point textureOffset() {
        return this.textureOffset;
    }

    public Size size() {
        return this.size;
    }

    public PositionedInnerTile atPos(Point pos) {
        return new PositionedInnerTile(pos, this);
    }
}
