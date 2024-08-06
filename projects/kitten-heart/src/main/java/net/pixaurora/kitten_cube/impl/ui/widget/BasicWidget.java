package net.pixaurora.kitten_cube.impl.ui.widget;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.WidgetSurface;

/**
 * A simple widget that can be clicked, and has a well defined surface.
 */
public interface BasicWidget extends Widget {
    public WidgetSurface surface();

    public default boolean isWithinBounds(Point pos) {
        return this.surface().isWithinBounds(pos);
    }
}
