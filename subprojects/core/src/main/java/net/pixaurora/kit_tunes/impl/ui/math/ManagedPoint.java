package net.pixaurora.kit_tunes.impl.ui.math;

import java.util.Optional;

import net.pixaurora.kit_tunes.impl.ui.screen.align.AlignmentStrategy;

public class ManagedPoint implements Point {
    private final Point relative;
    private Optional<Point> exact;

    public ManagedPoint(Point relativePos) {
        this.relative = relativePos;
        this.exact = Optional.empty();
    }

    public Point relative() {
        return this.relative;
    }

    public Point exact() {
        return this.exact.orElse(relative);
    }

    public void align(AlignmentStrategy strategy, Size window) {
        this.exact = Optional.of(strategy.align(relative, window));
    }

    @Override
    public int x() {
        return this.exact().x();
    }

    @Override
    public int y() {
        return this.exact().y();
    }
}
