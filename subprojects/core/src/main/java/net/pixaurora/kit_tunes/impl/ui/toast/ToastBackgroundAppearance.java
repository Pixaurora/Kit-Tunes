package net.pixaurora.kit_tunes.impl.ui.toast;

import java.util.Arrays;
import java.util.List;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public class ToastBackgroundAppearance {
    private final ResourcePath texture;
    private final Size size;

    private final Point centerOffset;
    private final Size centerSize;

    public ToastBackgroundAppearance(ResourcePath texture, Size size, Point centerTilePos, Size centerTileSize) {
        this.texture = texture;
        this.size = size;
        this.centerOffset = centerTilePos;
        this.centerSize = centerTileSize;
    }

    public ResourcePath texture() {
        return this.texture;
    }

    public Size size() {
        return this.size;
    }

    public Point centerTilePos() {
        return this.centerOffset;
    }

    public List<List<ToastBackgroundTile>> initColumns() {
        Point first = Point.ZERO;
        Point middle = this.centerOffset;
        Point last = this.centerOffset.offset(this.centerSize);

        return Arrays.asList(
                Arrays.asList(new ToastBackgroundTile(first, this.topLeftSize()),
                        new ToastBackgroundTile(first.withYOf(middle), this.middleLeftSize()),
                        new ToastBackgroundTile(first.withYOf(last), this.bottomLeftSize())),
                Arrays.asList(new ToastBackgroundTile(middle.withYOf(first), this.topMiddleSize()),
                        new ToastBackgroundTile(middle, this.centerSize()),
                        new ToastBackgroundTile(middle.withYOf(last), this.bottomMiddleSize())),
                Arrays.asList(new ToastBackgroundTile(last.withYOf(first), this.topRightSize()),
                        new ToastBackgroundTile(last.withYOf(middle), this.middleRightSize()),
                        new ToastBackgroundTile(last, this.bottomRightSize())));
    }

    public int leftWidth() {
        return centerOffset.x();
    }

    public int middleWidth() {
        return centerSize.x();
    }

    public int rightWidth() {
        return size.x() - leftWidth() - middleWidth();
    }

    public int topHeight() {
        return centerOffset.y();
    }

    public int middleHeight() {
        return centerSize.y();
    }

    public int bottomHeight() {
        return size.y() - topHeight() - middleHeight();
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
        return centerSize;
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
