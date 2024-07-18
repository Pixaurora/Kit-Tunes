package net.pixaurora.kitten_cube.impl.ui.toast;

import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.tile.InnerTile;
import net.pixaurora.kitten_cube.impl.ui.tile.PositionedInnerTile;
import net.pixaurora.kitten_heart.impl.util.Pair;

public class ToastBackground {
    private final ToastBackgroundAppearance appearance;

    private final Point iconPos;

    private final Point titlePos;

    private final Point linesStartPos;
    private final int maxLineLength;

    private final int rightPadding;
    private final int bottomPadding;

    public ToastBackground(ToastBackgroundAppearance appearance, Point iconPos, Point titlePos, Point linesStartPos,
            int maxLineLength, int rightPadding, int bottomPadding) {
        this.appearance = appearance;
        this.iconPos = iconPos;
        this.titlePos = titlePos;
        this.linesStartPos = linesStartPos;
        this.maxLineLength = maxLineLength;
        this.rightPadding = rightPadding;
        this.bottomPadding = bottomPadding;
    }

    public Pair<List<PositionedInnerTile>, Size> tilesAndSize(Size textSize) {
        Size minimumSize = textSize.offset(bodyTextStartPos()).offset(rightPadding, bottomPadding);

        Size corners = this.appearance.topLeftSize().offset(this.appearance.bottomRightSize());
        Size centerSegmentCounts = Size.of(
                (int) Math.ceil(
                        Math.max(1, (float) (minimumSize.width() - corners.width()) / this.appearance.middleWidth())),
                (int) Math.ceil(Math.max(1,
                        (float) (minimumSize.height() - corners.height()) / this.appearance.middleHeight())));

        List<List<InnerTile>> columns = this.appearance.initColumns();

        List<PositionedInnerTile> arrangedTiles = new ArrayList<>();
        Point pos = Point.ZERO;

        for (int i = 0; i < columns.size(); i++) {
            List<InnerTile> column = columns.get(i);
            int repetitionCount = i == 1 ? centerSegmentCounts.x() : 1; // If in the middle, draw the middle part
                                                                        // multiple times.

            for (int repetition = 0; repetition < repetitionCount; repetition++) {
                pos = pos.withY(0);

                InnerTile topTile = column.get(0);

                arrangedTiles.add(topTile.atPos(pos));
                pos = pos.offset(0, topTile.size().height());

                InnerTile middleTile = column.get(1);
                for (int middlePart = 0; middlePart <= centerSegmentCounts.y(); middlePart++) {
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

    public ToastBackgroundAppearance appearance() {
        return this.appearance;
    }

    public Point iconPos() {
        return this.iconPos;
    }

    public Point titlePos() {
        return this.titlePos;
    }

    public Point bodyTextStartPos() {
        return this.linesStartPos;
    }

    public int maxLineLength() {
        return this.maxLineLength;
    }
}
