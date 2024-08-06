package net.pixaurora.kitten_cube.impl.ui.screen.align;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;

public interface AlignmentStrategy {
    public Point align(Point original, Size window);

    public Point inverseAlign(Point aligned, Size window);
}
