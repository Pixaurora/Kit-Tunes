package net.pixaurora.kit_tunes.impl.ui.widget;

import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.screen.ScreenHandle;
import net.pixaurora.kit_tunes.impl.ui.texture.Texture;

public class StaticIcon implements Widget {
	private final Texture icon;
	private final Point startPoint;

	private final Point endPoint;

	public StaticIcon(Texture icon, Point pos) {
		this.icon = icon;
		this.startPoint = pos;
		this.endPoint = pos.offset(icon.size());
	}

	@Override
	public void draw(ScreenHandle handle, GuiDisplay gui, Point mousePos) {
		gui.drawTexture(this.icon, this.startPoint);
	}

	@Override
	public boolean isWithinBounds(Point mousePos) {
		return this.startPoint.lessThan(mousePos) && mousePos.lessThan(this.endPoint);
	}

	@Override
	public void onClick() {
		KitTunes.LOGGER.info("Meow! I'm an image!");
	}

}
