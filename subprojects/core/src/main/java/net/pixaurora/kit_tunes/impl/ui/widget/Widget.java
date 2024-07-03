package net.pixaurora.kit_tunes.impl.ui.widget;

import java.util.function.Function;

import net.pixaurora.kit_tunes.impl.ui.Drawable;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.widget.surface.ClickableSurface;

public interface Widget extends Drawable, ClickableSurface {
    public Widget mapPoints(Function<Point, Point> mapping);
}
