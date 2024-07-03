package net.pixaurora.kit_tunes.impl.ui.screen;

import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.ManagedPoint;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kit_tunes.impl.ui.widget.Widget;

public abstract class ScreenTemplate implements Screen {
    private boolean initializedWidgets = false;

    private final List<ManagedPoint> managedPoints = new ArrayList<>();
    private final List<Widget> widgets = new ArrayList<>();

    @Override
    public final void draw(GuiDisplay gui, Point mousePos) {
        for (Widget widget : this.widgets) {
            widget.draw(gui, mousePos);
        }
    }

    @Override
    public final void init(Size window) {
        if (!this.initializedWidgets) {
            this.initializedWidgets = true;
            this.firstInit();
        }

        this.managePoints(window);
    }

    @Override
    public final void handleClick(Point mousePos, int button) {
        for (Widget widget : this.widgets) {
            if (widget.isWithinBounds(mousePos)) {
                widget.onClick(mousePos);
                return;
            }
        }
    }

    private void managePoints(Size window) {
        AlignmentStrategy alignment = this.alignmentMethod();

        for (ManagedPoint point : this.managedPoints) {
            point.align(alignment, window);
        }
    }

    private Point manage(Point pos) {
        ManagedPoint managedPos = new ManagedPoint(pos);

        this.managedPoints.add(managedPos);
        return managedPos;
    }

    protected final void addWidget(Widget widget) {
        this.widgets.add(widget.mapPoints(this::manage));
    }

    protected abstract AlignmentStrategy alignmentMethod();

    protected abstract void firstInit();

}
