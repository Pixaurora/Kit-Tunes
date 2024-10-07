package net.pixaurora.kitten_cube.impl.ui.toast;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.tile.InnerTileGrid;

public class ToastBackground {
    private final InnerTileGrid appearance;

    private final Point iconPos;

    private final Point titlePos;
    private final boolean isTitleCentered;

    private final Point linesStartPos;
    private final int maxLineLength;

    private final int rightPadding;
    private final int bottomPadding;

    public ToastBackground(InnerTileGrid appearance, Point iconPos, Point titlePos, boolean isTitleCentered,
            Point linesStartPos, int maxLineLength, int rightPadding, int bottomPadding) {
        this.appearance = appearance;
        this.iconPos = iconPos;
        this.titlePos = titlePos;
        this.isTitleCentered = isTitleCentered;
        this.linesStartPos = linesStartPos;
        this.maxLineLength = maxLineLength;
        this.rightPadding = rightPadding;
        this.bottomPadding = bottomPadding;
    }

    public Size padding() {
        return Size.of(rightPadding, bottomPadding);
    }

    public InnerTileGrid appearance() {
        return this.appearance;
    }

    public Point iconPos() {
        return this.iconPos;
    }

    public Point titlePos() {
        return this.titlePos;
    }

    public boolean isTitleCentered() {
        return this.isTitleCentered;
    }

    public Point bodyTextStartPos() {
        return this.linesStartPos;
    }

    public int maxLineLength() {
        return this.maxLineLength;
    }
}
