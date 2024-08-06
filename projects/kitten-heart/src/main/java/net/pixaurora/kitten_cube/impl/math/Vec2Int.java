package net.pixaurora.kitten_cube.impl.math;

public interface Vec2Int {
    public int x();

    public int y();

    public default boolean lessThan(Vec2Int other) {
        return this.x() < other.x() && this.y() < other.y();
    }
}
