package net.pixaurora.kit_tunes.impl.gui;

import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.screen.ScreenHandle;
import net.pixaurora.kit_tunes.impl.ui.widget.Widget;
import net.pixaurora.kit_tunes.impl.ui.widget.WidgetHandle;

public class WidgetImpl implements WidgetHandle, GuiEventListener, NarratableEntry {
    private final Widget widget;
    private final ScreenHandle screen;

    public WidgetImpl(Widget widget, ScreenHandle screen) {
        this.widget = widget;
        this.screen = screen;
    }

    public Widget coreVersion() {
        return this.widget;
    }

    // Bridge functions

    @Override
    public ScreenHandle screen() {
        return this.screen;
    }

    // Minecraft implementation functions

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
        Point mousePos = Point.of((int) x, (int) y);

        if (widget.isWithinBounds(mousePos)) {
            widget.onClick(this, mousePos);
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
