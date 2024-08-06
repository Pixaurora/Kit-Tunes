package net.pixaurora.kitten_cube.impl.ui.tile;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;

public class PositionedInnerTile {
    private final Point pos;
    private final InnerTile tile;

    public PositionedInnerTile(Point pos, InnerTile tile) {
        this.pos = pos;
        this.tile = tile;
    }

    public Size size() {
        return this.tile.size();
    }

    public void draw(GuiDisplay gui) {
        gui.drawGui(tile.texture(), this.pos, tile.size(), tile.textureOffset());
    }
}
