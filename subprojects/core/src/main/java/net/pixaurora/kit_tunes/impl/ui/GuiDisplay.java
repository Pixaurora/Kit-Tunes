package net.pixaurora.kit_tunes.impl.ui;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;
import net.pixaurora.kit_tunes.impl.ui.texture.GuiTexture;
import net.pixaurora.kit_tunes.impl.ui.texture.Texture;

public interface GuiDisplay {
	public void drawTexture(ResourcePath path, Size size, Point pos);

	public void drawGuiTexture(ResourcePath path, Size size, Point pos);

	public void drawText(Component text, Color color, Point pos);

	public default void drawTexture(Texture texture, Point pos) {
		this.drawTexture(texture.path(), texture.size(), pos);
	}

	public default void drawGuiTexture(GuiTexture texture, Point pos) {
		this.drawGuiTexture(texture.path(), texture.size(), pos);
	}
}
