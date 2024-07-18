package net.pixaurora.kit_tunes.impl.ui.math;

public interface Vec2Int {
    public int x();

    public int y();

    public default boolean lessThan(Vec2Int other) {
        return this.x() < other.x() && this.y() < other.y();
    }
}
