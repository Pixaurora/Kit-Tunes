package net.pixaurora.kitten_heart.impl.ui.widget.progress;

public class ProgressBarTileSets {
    private final ProgressBarTileSet empty;
    private final ProgressBarTileSet filled;

    public ProgressBarTileSets(ProgressBarTileSet empty, ProgressBarTileSet filled) {
        this.empty = empty;
        this.filled = filled;
    }

    public ProgressBarTileSet empty() {
        return this.empty;
    }

    public ProgressBarTileSet filled() {
        return this.filled;
    }
}
