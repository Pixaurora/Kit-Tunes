package net.pixaurora.kitten_cube.impl.ui.widget;

import java.util.Optional;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.Drawable;
import net.pixaurora.kitten_cube.impl.ui.screen.WidgetContainer;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.widget.event.WindowUpdateEvent;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.ClickableSurface;

public interface Widget extends Drawable, ClickableSurface {
    public default void init(WidgetContainer<?> container) {
    }

    public default void onWindowUpdate(WindowUpdateEvent event) {
    }

    public default void tick() {
    }

    public default Optional<Alignment> alignmentMethod() {
        return Optional.empty();
    }

    public Size size();
}
