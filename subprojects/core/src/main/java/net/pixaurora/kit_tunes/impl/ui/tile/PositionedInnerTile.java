package net.pixaurora.kit_tunes.impl.ui.tile;

import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

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
