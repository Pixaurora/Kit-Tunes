package net.pixaurora.kit_tunes.impl.ui.widget.surface;

import java.util.function.Function;

import net.pixaurora.kit_tunes.impl.ui.math.Point;

public interface WidgetSurface {
    public boolean isWithinBounds(Point mousePos);

    public WidgetSurface mapPoints(Function<Point, Point> mapping);
}
