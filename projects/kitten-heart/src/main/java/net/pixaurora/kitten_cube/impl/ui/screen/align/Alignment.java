package net.pixaurora.kitten_cube.impl.ui.screen.align;

import java.util.function.Function;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;

public enum Alignment implements AlignmentStrategy {
    CENTER(window -> window.midPoint()), CENTER_TOP(window -> window.midPoint().withY(0)),
    CENTER_BOTTOM(window -> window.midPoint().withYOf(window)),
    TOP_LEFT(window -> Point.ZERO);

    private final Function<Size, Point> offsetRule;

    private Alignment(Function<Size, Point> offsetRule) {
        this.offsetRule = offsetRule;
    }

    @Override
    public Point align(Point original, Size window) {
        return original.offset(this.offsetRule.apply(window));
    }

    @Override
    public Point inverseAlign(Point original, Size window) {
        return original.offset(this.offsetRule.apply(window).scaledBy(-1));
    }
}
