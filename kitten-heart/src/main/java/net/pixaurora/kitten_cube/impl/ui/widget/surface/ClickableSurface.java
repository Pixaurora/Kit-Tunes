package net.pixaurora.kitten_cube.impl.ui.widget.surface;

import net.pixaurora.kitten_cube.impl.math.Point;

public interface ClickableSurface extends WidgetSurface {
    public void onClick(Point mousePos);
}
