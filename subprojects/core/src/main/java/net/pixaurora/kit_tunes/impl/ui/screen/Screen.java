package net.pixaurora.kit_tunes.impl.ui.screen;

import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public interface Screen {
	public void init(ScreenHandle handle, Size window);

	public void draw(ScreenHandle handle, GuiDisplay gui);
}
