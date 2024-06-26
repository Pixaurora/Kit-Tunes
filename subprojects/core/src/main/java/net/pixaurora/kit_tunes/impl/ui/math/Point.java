package net.pixaurora.kit_tunes.impl.ui.math;

public class Point implements Vec2Int<Point> {
    public static final Point ZERO = new Point(0, 0);

    private final int x;
    private final int y;

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    @Override
    public Point constructVec(int x, int y) {
        return of(x, y);
    }

    @Override
    public int x() {
        return this.x;
    }

    @Override
    public int y() {
        return this.y;
    }
}
