package net.pixaurora.kit_tunes.impl.ui.widget;

import net.pixaurora.kit_tunes.impl.ui.Drawable;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.widget.surface.ClickableSurface;
import net.pixaurora.kit_tunes.impl.ui.widget.surface.WidgetSurface;

public interface Widget extends Drawable<WidgetHandle>, ClickableSurface {
    public WidgetSurface surface();

    @Override
    public default boolean isWithinBounds(Point mousePos) {
        return this.surface().isWithinBounds(mousePos);
    }
}
