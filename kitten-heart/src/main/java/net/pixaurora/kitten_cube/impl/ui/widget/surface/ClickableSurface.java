package net.pixaurora.kitten_cube.impl.ui.widget.surface;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;

public interface ClickableSurface extends WidgetSurface {
    public void onClick(Point mousePos, MouseButton button);
}
