package net.pixaurora.kitten_cube.impl.math;

public interface Size extends Vec2Int {
    public static Size of(int width, int height) {
        return new SizeImpl(width, height);
    }

    public default Point toPoint() {
        return Point.of(this.x(), this.y());
    }

    public default int width() {
        return this.x();
    }

    public default int height() {
        return this.y();
    }

    public default Point midPoint() {
        return this.divideBy(2).toPoint();
    }

    public default Point centerWithinSelf(Size other) {
        return other.centerOn(this.midPoint());
    }

    public default Point centerOn(Point targetMidpoint) {
        return targetMidpoint.offset(this.divideBy(-2));
    }

    public default Point centerVertically(Point targetMidpoint) {
        return targetMidpoint.offset(0, this.divideBy(-2).y());
    }

    public default Point centerHorizontally(Point targetMidpoint) {
        return targetMidpoint.offset(this.divideBy(-2).x(), 0);
    }

    public default Size overlay(Size other) {
        return Size.of(Math.max(this.x(), other.x()), Math.max(this.y(), other.y()));
    }

    // Functions in common with Point

    public default Size offset(Vec2Int by) {
        return this.offset(by.x(), by.y());
    }

    public default Size offset(int x, int y) {
        return Size.of(this.x() + x, this.y() + y);
    }

    public default Size scaledBy(int value) {
        return Size.of(this.x() * value, this.y() * value);
    }

    public default Size divideBy(int value) {
        return Size.of(this.x() / value, this.y() / value);
    }

    public default Size withX(int x) {
        return Size.of(x, this.y());
    }

    public default Size withY(int y) {
        return Size.of(this.x(), y);
    }

    public default Size withXOf(Vec2Int other) {
        return this.withX(other.x());
    }

    public default Size withYOf(Vec2Int other) {
        return this.withY(other.y());
    }
}
