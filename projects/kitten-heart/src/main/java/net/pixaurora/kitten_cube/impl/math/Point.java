package net.pixaurora.kitten_cube.impl.math;

public interface Point extends Vec2Int {
    public static final Point ZERO = Point.of(0, 0);

    public static Point of(int x, int y) {
        return new PointImpl(x, y);
    }

    public default Point midPointBetween(Vec2Int other) {
        return Point.of((this.x() + other.x()) / 2, (this.y() + other.y()) / 2);
    }

    // Functions in common with Size

    public default Point offset(Vec2Int by) {
        return this.offset(by.x(), by.y());
    }

    public default Point offset(int x, int y) {
        return Point.of(this.x() + x, this.y() + y);
    }

    public default Point scaledBy(int value) {
        return Point.of(this.x() * value, this.y() * value);
    }

    public default Point divideBy(int value) {
        return Point.of(this.x() / value, this.y() / value);
    }

    public default Point withX(int x) {
        return Point.of(x, this.y());
    }

    public default Point withY(int y) {
        return Point.of(this.x(), y);
    }

    public default Point withXOf(Vec2Int other) {
        return this.withX(other.x());
    }

    public default Point withYOf(Vec2Int other) {
        return this.withY(other.y());
    }
}
