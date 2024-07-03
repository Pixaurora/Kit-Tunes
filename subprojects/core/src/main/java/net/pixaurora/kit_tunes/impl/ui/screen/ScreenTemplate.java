package net.pixaurora.kit_tunes.impl.ui.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kit_tunes.impl.ui.AlignedGuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.screen.align.PointManager;
import net.pixaurora.kit_tunes.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kit_tunes.impl.ui.widget.Widget;

public abstract class ScreenTemplate implements Screen {
    private boolean initializedWidgets = false;

    private Optional<PointManager> pointAligner = Optional.empty();
    private Optional<Size> size = Optional.empty();

    private final List<Widget> widgets = new ArrayList<>();

    @Override
    public final void draw(GuiDisplay gui, Point mousePos) {
        GuiDisplay alignedGui = new AlignedGuiDisplay(gui, this.pointAligner.get());
        mousePos = this.alignmentMethod().inverseAlign(mousePos, this.size.get());

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
        mousePos = this.alignmentMethod().inverseAlign(mousePos, this.size.get());

        for (Widget widget : this.widgets) {
            if (widget.isWithinBounds(mousePos)) {
                widget.onClick(mousePos);
                return;
            }
        }
    }

    private void updateWindow(Size window) {
        this.pointAligner = Optional.of(new PointManager(this.alignmentMethod(), window));
        this.size = Optional.of(window);
    }

    protected final void addWidget(Widget widget) {
        this.widgets.add(widget);
    }

    protected abstract AlignmentStrategy alignmentMethod();

    protected abstract void firstInit();

}
