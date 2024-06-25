package net.pixaurora.kit_tunes.impl.ui.widget.surface;

import net.pixaurora.kit_tunes.impl.ui.math.Point;

public interface ClickableSurface {
	public boolean isWithinBounds(Point mousePos);

	public void onClick();
}
