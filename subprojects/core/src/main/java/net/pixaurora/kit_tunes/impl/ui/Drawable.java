package net.pixaurora.kit_tunes.impl.ui;

import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.screen.ScreenHandle;

public interface Drawable {
	public void draw(ScreenHandle handle, GuiDisplay gui, Point mousePos);
}
