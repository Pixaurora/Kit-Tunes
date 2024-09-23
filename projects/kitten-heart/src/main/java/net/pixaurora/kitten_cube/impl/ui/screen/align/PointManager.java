package net.pixaurora.kitten_cube.impl.ui.screen.align;

import java.util.HashMap;
import java.util.Map;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;

public class PointManager {
    private final Map<Point, Point> cache;

    private final AlignmentStrategy strategy;
    private final Size window;

    public PointManager(AlignmentStrategy alignment, Size window) {
        this.cache = new HashMap<>();
        this.strategy = alignment;
        this.window = window;
    }

    public Point align(Point pos) {
        return this.cache.computeIfAbsent(pos, point -> strategy.align(pos, window));
    }

    public Point inverseAlign(Point pos) {
        return this.strategy.inverseAlign(pos, window);
    }
}
