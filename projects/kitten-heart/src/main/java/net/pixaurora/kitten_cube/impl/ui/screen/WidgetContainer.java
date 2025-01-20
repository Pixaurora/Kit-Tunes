package net.pixaurora.kitten_cube.impl.ui.screen;

import java.util.Optional;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.NestedAlignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.WidgetAnchor;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;
import net.pixaurora.kitten_cube.impl.ui.widget.event.WindowUpdateEvent;

public class WidgetContainer<T extends Widget> {
    private final T widget;
    private final ScreenTemplate screen;

    private Point pos;
    private Optional<Alignment> baseAlignment;
    private Alignment realAlignment;

    public WidgetContainer(T widget, ScreenTemplate screen) {
        this.widget = widget;
        this.screen = screen;
        this.pos = Point.ZERO;
        this.baseAlignment = Optional.empty();
        this.realAlignment = new SelfAlignment(this, WidgetAnchor.TOP_LEFT);
    }

    public WidgetContainer<T> align(Alignment newAlignment) {
        this.baseAlignment = Optional.of(newAlignment);

        return this;
    }

    public WidgetContainer<T> anchor(WidgetAnchor newAnchor) {
        this.realAlignment = new SelfAlignment(this, newAnchor);

        return this;
    }

    public WidgetContainer<T> at(Point newPos) {
        this.pos = newPos;

        return this;
    }

    public T get() {
        return this.widget;
    }

    public Point pos() {
        return this.pos;
    }

    public Size size() {
        return this.widget.size();
    }

    private Optional<Alignment> customizedAlignment() {
        if (this.baseAlignment.isPresent()) {
            return this.baseAlignment;
        } else {
            return this.widget.alignmentMethod();
        }
    }

    public Alignment baseAlignment() {
        return this.customizedAlignment().orElseGet(this.screen::alignmentMethod);
    }

    public Alignment realAlignment() {
        return this.realAlignment;
    }

    public Alignment relativeTo(WidgetAnchor anchor) {
        return new RelativeToSelfAlignment(this, anchor);
    }

    public void onWindowUpdate(WindowUpdateEvent event) {
        this.widget.onWindowUpdate(event);
    }

    private static class SelfAlignment implements NestedAlignment {
        private final WidgetContainer<?> widget;
        private final WidgetAnchor anchor;

        public SelfAlignment(WidgetContainer<?> widget, WidgetAnchor anchor) {
            this.widget = widget;
            this.anchor = anchor;
        }

        @Override
        public Alignment parent() {
            return this.widget.baseAlignment();
        }

        @Override
        public int offsetX() {
            return this.widget.pos().x() - this.anchor.anchorX(this.widget.get());
        }

        @Override
        public int offsetY() {
            return this.widget.pos().y() - this.anchor.anchorY(this.widget.get());
        }
    }

    private static class RelativeToSelfAlignment implements NestedAlignment {
        private final WidgetContainer<?> widget;
        private final WidgetAnchor anchor;

        public RelativeToSelfAlignment(WidgetContainer<?> widget, WidgetAnchor anchor) {
            this.widget = widget;
            this.anchor = anchor;
        }

        @Override
        public Alignment parent() {
            return this.widget.realAlignment;
        }

        @Override
        public int offsetX() {
            return this.anchor.anchorX(this.widget.get());
        }

        @Override
        public int offsetY() {
            return this.anchor.anchorY(this.widget.get());
        }
    }
}
