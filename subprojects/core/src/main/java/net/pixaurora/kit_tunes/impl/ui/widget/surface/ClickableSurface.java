package net.pixaurora.kit_tunes.impl.ui.widget.surface;

import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.widget.WidgetHandle;

public interface ClickableSurface extends WidgetSurface {
    public void onClick(WidgetHandle handle, Point mousePos);
}
