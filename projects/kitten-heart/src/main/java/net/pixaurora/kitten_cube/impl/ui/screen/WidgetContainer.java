package net.pixaurora.kitten_cube.impl.ui.screen;

import java.util.Optional;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.screen.align.PointManager;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public class WidgetContainer<T extends Widget> {
    private final T widget;
    private Optional<PointManager> aligner;

    public WidgetContainer(T widget) {
        this.widget = widget;
        this.aligner = Optional.empty();
    }

    public T get() {
        return this.widget;
    }

    public Optional<PointManager> customizedAligner() {
        return this.aligner;
    }

    public void onWindowUpdate(Size window) {
        this.widget.onWindowUpdate(window);
        this.aligner = this.widget.alignmentMethod().map(alignment -> new PointManager(alignment, window));
    }
}
