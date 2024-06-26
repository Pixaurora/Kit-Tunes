package net.pixaurora.kit_tunes.impl.ui.toast;

import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public class ToastBackgroundTile {
    private final Point pos;

    private final Point textureOffset;
    private final Size size;

    public ToastBackgroundTile(Point pos, Point textureOffset, Size size) {
        this.pos = pos;
        this.textureOffset = textureOffset;
        this.size = size;
    }

    public ToastBackgroundTile(Point textureOffset, Size size) {
        this(Point.ZERO, textureOffset, size);
    }

    public ToastBackgroundTile atPos(Point pos) {
        return new ToastBackgroundTile(pos, this.textureOffset, this.size);
    }

    public Point pos() {
        return this.pos;
    }

    public Point textureOffset() {
        return this.textureOffset;
    }

    public Size size() {
        return this.size;
    }
}
