package net.pixaurora.kit_tunes.impl.ui.widget.surface;

import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public class RectangularSurface implements WidgetSurface {
    private final Point startPos;
    private final Point endPos;

    private RectangularSurface(Point startPos, Size size) {
        this.startPos = startPos;
        this.endPos = startPos.offset(size);
    }

    public static RectangularSurface of(Point startPos, Size size) {
        return new RectangularSurface(startPos, size);
    }

    public Point startPos() {
        return this.startPos;
    }

    @Override
    public boolean isWithinBounds(Point mousePos) {
        return this.startPos.lessThan(mousePos) && mousePos.lessThan(this.endPos);
    }
}
