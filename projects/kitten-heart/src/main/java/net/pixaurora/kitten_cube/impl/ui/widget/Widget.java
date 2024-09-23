package net.pixaurora.kitten_cube.impl.ui.widget;

import java.util.Optional;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.Drawable;
import net.pixaurora.kitten_cube.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.ClickableSurface;

public interface Widget extends Drawable, ClickableSurface {
    public default void onWindowUpdate(Size window) {
    }

    public default Optional<AlignmentStrategy> alignmentMethod() {
        return Optional.empty();
    }
}
