package net.pixaurora.kitten_cube.impl.math;

public class PointImpl implements Point {
    private final int x;
    private final int y;

    public PointImpl(int x, int y) {
        this.x = x;
        this.y = y;
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
