package net.pixaurora.kitten_cube.impl.ui.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_heart.impl.util.Pair;

public class InnerTileGrid {
    private final GuiTexture texture;

    private final Point centerOffset;
    private final Size centerSize;

    public InnerTileGrid(GuiTexture texture, Point centerTilePos, Size centerTileSize) {
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

    public Pair<List<PositionedInnerTile>, Size> tilesAndSize(Point startPoint, Size minimumSize) {
        return tilesAndSize(startPoint, minimumSize, false);
    }

    public Pair<List<PositionedInnerTile>, Size> tilesAndSize(Point startPoint, Size minimumSize,
            boolean adjustStartPoint) {
        Size corners = this.topLeftSize().offset(this.bottomRightSize());
        Size centerSegmentCounts = Size.of(
                (int) Math.ceil(
                        Math.max(1, (float) (minimumSize.width() - corners.width()) / this.middleWidth())),
                (int) Math.ceil(Math.max(1,
                        (float) (minimumSize.height() - corners.height()) / this.middleHeight())));

        if (adjustStartPoint) {
            Size realSize = Size.of(centerSegmentCounts.width() * this.middleWidth() + corners.width(),
                    centerSegmentCounts.height() * this.middleHeight() + corners.height());

            Size difference = realSize.scaledBy(-1).offset(minimumSize);

            startPoint = startPoint.offset(difference.divideBy(2));
        }

        List<List<InnerTile>> columns = this.initColumns();

        List<PositionedInnerTile> arrangedTiles = new ArrayList<>();
        Point pos = startPoint;

        for (int i = 0; i < columns.size(); i++) {
            List<InnerTile> column = columns.get(i);
            int repetitionCount = i == 1 ? centerSegmentCounts.x() : 1; // If in the middle, draw the middle part
                                                                        // multiple times.

            for (int repetition = 0; repetition < repetitionCount; repetition++) {
                pos = pos.withYOf(startPoint);

                InnerTile topTile = column.get(0);

                arrangedTiles.add(topTile.atPos(pos));
                pos = pos.offset(0, topTile.size().height());

                InnerTile middleTile = column.get(1);
                for (int middlePart = 0; middlePart < centerSegmentCounts.y(); middlePart++) {
                    arrangedTiles.add(middleTile.atPos(pos));
                    pos = pos.offset(0, middleTile.size().height());
                }

                InnerTile bottomTile = column.get(2);

                arrangedTiles.add(bottomTile.atPos(pos));
                pos = pos.offset(bottomTile.size());
            }
        }

        Size toastSize = Size.of(pos.x(), pos.y()); // After iterating through the columns, the offset from the start =
                                                    // the size.

        return Pair.of(arrangedTiles, toastSize);
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
