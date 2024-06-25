package net.pixaurora.kit_tunes.impl.gui;

import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.widget.Widget;

public class WidgetImpl implements GuiEventListener, NarratableEntry {
	private final Widget widget;

	public WidgetImpl(Widget widget) {
		this.widget = widget;
	}

	@Override
	public void setFocused(boolean focused) {
		// TODO: support tab completion & stuff?
	}

	@Override
	public boolean isFocused() {
		return false;
	}

	@Override
	public boolean mouseClicked(double x, double y, int button) {
		if (widget.isWithinBounds(Point.of((int) x, (int) y))) {
			widget.onClick();
			return true;
		} else {
			return false;
		}
	}

	// TODO: add support for this to library?
	@Override
	public void updateNarration(NarrationElementOutput builder) {
	}

	@Override
	public NarrationPriority narrationPriority() {
		return NarrationPriority.NONE;
	}
}
