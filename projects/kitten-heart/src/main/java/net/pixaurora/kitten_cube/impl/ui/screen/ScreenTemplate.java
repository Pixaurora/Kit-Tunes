package net.pixaurora.kitten_cube.impl.ui.screen;

import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.AlignedGuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;
import net.pixaurora.kitten_cube.impl.ui.widget.event.WindowUpdateEvent;
import net.pixaurora.kitten_cube.impl.ui.widget.event.WindowUpdateEventImpl;

public abstract class ScreenTemplate implements Screen {
    private boolean initializedWidgets = false;

    private final List<WidgetContainer<?>> widgets = new ArrayList<>();

    private Size window;

    @Override
    public final void draw(GuiDisplay gui, Point mousePos) {
        for (WidgetContainer<?> widget : this.widgets) {
            Alignment alignment = widget.realAlignment();

            GuiDisplay alignedGui = new AlignedGuiDisplay(gui, alignment, this.window);
            Point alignedMousePos = alignment.inverseAlign(mousePos, window);

            widget.get().draw(alignedGui, alignedMousePos);
        }
    }

    @Override
    public final void init(Size window) {
        this.window = window;

        if (!this.initializedWidgets) {
            this.initializedWidgets = true;
            this.addBackground();
            this.firstInit();
        }

        this.updateWindow(window);
    }

    @Override
    public final void handleClick(Point mousePos, MouseButton button) {
        for (WidgetContainer<?> widget : this.widgets) {
            Alignment aligner = widget.realAlignment();

            Point alignedMousePos = aligner.inverseAlign(mousePos, this.window);

            if (widget.get().isWithinBounds(alignedMousePos)) {
                widget.get().onClick(alignedMousePos, button);
                return;
            }
        }
    }

    @Override
    public final void handleTick() {
        for (WidgetContainer<?> widget : this.widgets) {
            widget.get().tick();
        }

        this.tick();
    }

    protected Size window() {
        return this.window;
    }

    private void updateWindow(Size window) {
        for (WidgetContainer<?> widget : this.widgets) {
            WindowUpdateEvent event = new WindowUpdateEventImpl(window, this);

            widget.onWindowUpdate(event);
        }
    }

    protected final <W extends Widget> WidgetContainer<W> addWidget(W widget) {
        WidgetContainer<W> widgetContainer = new WidgetContainer<>(widget, this);
        this.widgets.add(widgetContainer);
        widgetContainer.onWindowUpdate(new WindowUpdateEventImpl(window, this));

        return widgetContainer;
    }

    protected final void removeWidget(WidgetContainer<?> widget) {
        this.widgets.remove(widget);
    }

    protected abstract Alignment alignmentMethod();

    protected void addBackground() {
    }

    protected void tick() {
    }

    protected abstract void firstInit();
}
