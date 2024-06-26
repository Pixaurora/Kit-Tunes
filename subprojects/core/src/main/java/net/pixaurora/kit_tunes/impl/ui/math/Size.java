package net.pixaurora.kit_tunes.impl.ui.math;

public class Size implements Vec2Int<Size> {
    private final int width;

    private final int height;

    private Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Size of(int x, int y) {
        return new Size(x, y);
    }

    public Point centerWithinSelf(Size sizeOfOther) {
        return this.divideBy(2) // Find the midpoint
                .offset(sizeOfOther.divideBy(-2)) // Subtract half of the other's size
                .toPoint();
    }

    public Point toPoint() {
        return Point.of(this.width, this.height);
    }

    @Override
    public Size constructVec(int x, int y) {
        return of(x, y);
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    @Override
    public int x() {
        return this.width;
    }

    @Override
    public int y() {
        return this.height;
    }
}
