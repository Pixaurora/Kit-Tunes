package net.pixaurora.kit_tunes.impl.ui.toast;

import java.util.Arrays;
import java.util.List;

import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.texture.GuiTexture;
import net.pixaurora.kit_tunes.impl.ui.tile.InnerTile;

public class ToastBackgroundAppearance {
    private final GuiTexture texture;

    private final Point centerOffset;
    private final Size centerSize;

    public ToastBackgroundAppearance(GuiTexture texture, Point centerTilePos, Size centerTileSize) {
        this.texture = texture;
        this.centerOffset = centerTilePos;
        this.centerSize = centerTileSize;
    }

    public GuiTexture texture() {
        return this.texture;
    }

    public Point centerTilePos() {
        return this.centerOffset;
    }

    public List<List<InnerTile>> initColumns() {
        Point first = Point.ZERO;
        Point middle = this.centerOffset;
        Point last = this.centerOffset.offset(this.centerSize);

        return Arrays.asList(
                Arrays.asList(new InnerTile(this.texture, first, this.topLeftSize()),
                        new InnerTile(this.texture, first.withYOf(middle), this.middleLeftSize()),
                        new InnerTile(this.texture, first.withYOf(last), this.bottomLeftSize())),
                Arrays.asList(new InnerTile(this.texture, middle.withYOf(first), this.topMiddleSize()),
                        new InnerTile(this.texture, middle, this.centerSize()),
                        new InnerTile(this.texture, middle.withYOf(last), this.bottomMiddleSize())),
                Arrays.asList(new InnerTile(this.texture, last.withYOf(first), this.topRightSize()),
                        new InnerTile(this.texture, last.withYOf(middle), this.middleRightSize()),
                        new InnerTile(this.texture, last, this.bottomRightSize())));
    }

    public int leftWidth() {
        return centerOffset.x();
    }

    public int middleWidth() {
        return centerSize.x();
    }

    public int rightWidth() {
        return this.texture.size().x() - leftWidth() - middleWidth();
    }

    public int topHeight() {
        return centerOffset.y();
    }

    public int middleHeight() {
        return centerSize.y();
    }

    public int bottomHeight() {
        return this.texture.size().y() - topHeight() - middleHeight();
    }

    public Size topLeftSize() {
        return Size.of(leftWidth(), topHeight());
    }

    public Size middleLeftSize() {
        return Size.of(leftWidth(), middleHeight());
    }

    public Size bottomLeftSize() {
        return Size.of(leftWidth(), bottomHeight());
    }

    public Size topMiddleSize() {
        return Size.of(middleWidth(), topHeight());
    }

    public Size centerSize() {
        return this.centerSize;
    }

    public Size bottomMiddleSize() {
        return Size.of(middleWidth(), bottomHeight());
    }

    public Size topRightSize() {
        return Size.of(rightWidth(), topHeight());
    }

    public Size middleRightSize() {
        return Size.of(rightWidth(), middleHeight());
    }

    public Size bottomRightSize() {
        return Size.of(rightWidth(), bottomHeight());
    }
}
