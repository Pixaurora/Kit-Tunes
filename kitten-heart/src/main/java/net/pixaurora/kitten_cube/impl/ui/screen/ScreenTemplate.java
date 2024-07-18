package net.pixaurora.kitten_cube.impl.ui.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.display.AlignedGuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kitten_cube.impl.ui.screen.align.PointManager;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public abstract class ScreenTemplate implements Screen {
    private boolean initializedWidgets = false;

    private Optional<PointManager> pointAligner = Optional.empty();
    private Optional<Size> window = Optional.empty();

    private final List<Widget> widgets = new ArrayList<>();

    @Override
    public final void draw(GuiDisplay gui, Point mousePos) {
        GuiDisplay alignedGui = new AlignedGuiDisplay(gui, this.pointAligner.get());
        mousePos = this.alignmentMethod().inverseAlign(mousePos, this.window.get());

        for (Widget widget : this.widgets) {
            widget.draw(alignedGui, mousePos);
        }
    }

    @Override
    public final void init(Size window) {
        if (!this.initializedWidgets) {
            this.initializedWidgets = true;
            this.firstInit();
        }

        this.updateWindow(window);
    }

    @Override
    public final void handleClick(Point mousePos, int button) {
        mousePos = this.alignmentMethod().inverseAlign(mousePos, this.window.get());

        for (Widget widget : this.widgets) {
            if (widget.isWithinBounds(mousePos)) {
                widget.onClick(mousePos);
                return;
            }
        }
    }

    private void updateWindow(Size window) {
        this.pointAligner = Optional.of(new PointManager(this.alignmentMethod(), window));
        this.window = Optional.of(window);
    }

    protected final <W extends Widget> W addWidget(W widget) {
        this.widgets.add(widget);
        return widget;
    }

    protected abstract AlignmentStrategy alignmentMethod();

    protected abstract void firstInit();

}
