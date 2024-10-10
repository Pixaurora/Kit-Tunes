package net.pixaurora.kitten_cube.impl.ui.widget.text;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.tile.InnerTileGrid;

public class TextLinesBackground {
    private final Point textStart;
    private final InnerTileGrid tiles;
    private final Size bottomRightPadding;

    public TextLinesBackground(Point textStart, InnerTileGrid tiles, Size bottomRightPadding) {
        this.textStart = textStart;
        this.tiles = tiles;
        this.bottomRightPadding = bottomRightPadding;
    }

    public Point textStart() {
        return textStart;
    }

    public InnerTileGrid grid() {
        return tiles;
    }

    public Size bottomRightPadding() {
        return this.bottomRightPadding;
    }
}
