package net.pixaurora.kit_tunes.impl.ui.math;

public class SizeImpl implements Size {
    private final int width;
    private final int height;

    public SizeImpl(int x, int y) {
        this.width = x;
        this.height = y;
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
