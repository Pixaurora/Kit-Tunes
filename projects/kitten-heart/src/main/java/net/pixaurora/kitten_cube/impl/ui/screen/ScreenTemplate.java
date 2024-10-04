package net.pixaurora.kitten_cube.impl.ui.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.AlignedGuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kitten_cube.impl.ui.screen.align.PointManager;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public abstract class ScreenTemplate implements Screen {
    private boolean initializedWidgets = false;

    private Optional<PointManager> defaultAligner = Optional.empty();

    private final List<WidgetContainer<?>> widgets = new ArrayList<>();

    private Size window;

    @Override
    public final void draw(GuiDisplay gui, Point mousePos) {
        PointManager defaultAligner = this.defaultAligner.get();

        for (WidgetContainer<?> widget : this.widgets) {
            PointManager aligner = widget.customizedAligner().orElse(defaultAligner);

            GuiDisplay alignedGui = new AlignedGuiDisplay(gui, aligner);
            Point alignedMousePos = aligner.inverseAlign(mousePos);

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
        PointManager defaultAligner = this.defaultAligner.get();

        for (WidgetContainer<?> widget : this.widgets) {
            PointManager aligner = widget.customizedAligner().orElse(defaultAligner);

            Point alignedMousePos = aligner.inverseAlign(mousePos);

            if (widget.get().isWithinBounds(alignedMousePos)) {
                widget.get().onClick(alignedMousePos, button);
                return;
            }
        }
    }

    @Override
    public void tick() {
        for (WidgetContainer<?> widget : this.widgets) {
            widget.get().tick();
        }
    }

    private void updateWindow(Size window) {
        this.defaultAligner = Optional.of(new PointManager(this.alignmentMethod(), window));

        for (WidgetContainer<?> widget : this.widgets) {
            widget.onWindowUpdate(window);
        }
    }

    protected final <W extends Widget> WidgetContainer<W> addWidget(W widget) {
        WidgetContainer<W> widgetContainer = new WidgetContainer<>(widget);
        this.widgets.add(widgetContainer);
        widgetContainer.onWindowUpdate(window);

        return widgetContainer;
    }

    protected final void removeWidget(WidgetContainer<?> widget) {
        this.widgets.remove(widget);
    }

    protected abstract AlignmentStrategy alignmentMethod();

    protected void addBackground() {
    }

    protected abstract void firstInit();
}
