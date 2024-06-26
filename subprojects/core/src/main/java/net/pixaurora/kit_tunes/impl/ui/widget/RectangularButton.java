package net.pixaurora.kit_tunes.impl.ui.widget;

import net.pixaurora.kit_tunes.impl.resource.ResourcePathImpl;
import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.screen.ScreenHandle;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;
import net.pixaurora.kit_tunes.impl.ui.text.TextProcessor;
import net.pixaurora.kit_tunes.impl.ui.texture.GuiTexture;

public class RectangularButton implements Widget {
	private static final GuiTexture DEFAULT_UNHIGLIGHTED_TEXTURE = GuiTexture
			.of(new ResourcePathImpl("minecraft", "textures/gui/sprites/widget/button.png"), Size.of(200, 20));
	private static final GuiTexture DEFAULT_HIGHLIGHTED_TEXTURE = GuiTexture
			.of(new ResourcePathImpl("minecraft", "textures/gui/sprites/widget/button_highlighted.png"), Size.of(200, 20));

	private final GuiTexture unhighlightedBackground;
	private final GuiTexture highlightedBackground;
	private final Point startPoint;

	private final Component text;
	private final Point textPos;

	private final ClickEvent action;

	private final Point endPoint;

	public RectangularButton(GuiTexture texture, GuiTexture hoverTexture, Point pos, Component text, TextProcessor font,
			ClickEvent action) {
		this.unhighlightedBackground = texture;
		this.highlightedBackground = hoverTexture;
		this.startPoint = pos;
		this.text = text;
		this.action = action;

		Size textSize = font.textSize(text);

		this.textPos = pos.offset(texture.size().centerWithinSelf(textSize));
		this.endPoint = pos.offset(texture.size());
	}

	public static RectangularButton vanillaButton(Point pos, Component text, TextProcessor font, ClickEvent action) {
		return new RectangularButton(DEFAULT_UNHIGLIGHTED_TEXTURE, DEFAULT_HIGHLIGHTED_TEXTURE, pos, text, font, action);
	}

	@Override
	public void draw(ScreenHandle handle, GuiDisplay gui, Point mousePos) {
		GuiTexture background = this.isWithinBounds(mousePos) ? this.highlightedBackground : this.unhighlightedBackground;
		gui.drawGuiTexture(background, this.startPoint);

		gui.drawText(this.text, Color.WHITE, this.textPos);
	}

	@Override
	public boolean isWithinBounds(Point mousePos) {
		return this.startPoint.lessThan(mousePos) && mousePos.lessThan(this.endPoint);
	}

	@Override
	public void onClick() {
		this.action.onClick();
	}

	public static interface ClickEvent {
		public void onClick();
	}
}
