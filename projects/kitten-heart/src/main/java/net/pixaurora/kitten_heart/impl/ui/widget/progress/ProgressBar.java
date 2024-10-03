package net.pixaurora.kitten_heart.impl.ui.widget.progress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kitten_cube.impl.ui.tile.InnerTile;
import net.pixaurora.kitten_cube.impl.ui.tile.PositionedInnerTile;
import net.pixaurora.kitten_cube.impl.ui.tile.TilePosition;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;
import net.pixaurora.kitten_heart.impl.KitTunes;

public class ProgressBar implements Widget {
    private final List<PositionedInnerTile> tiles;
    private Optional<Size> window;
    private int progressWidth;

    private final ProgressProvider progressProvider;
    private final ProgressBarTileSets tilesets;

    public ProgressBar(ProgressProvider progressProvider, ProgressBarTileSets tilesets) {
        this.tiles = new ArrayList<>();
        this.window = Optional.empty();
        this.progressProvider = progressProvider;
        this.tilesets = tilesets;
    }

    @Override
    public void onWindowUpdate(Size window) {
        this.window = Optional.of(window);
        this.update(true);
    }

    @Override
    public void tick() {
        this.update(false);
    }

    private void update(boolean windowChanged) {
        if (!this.window.isPresent()) {
            return;
        }

        Size window = this.window.get();
        double songProgress = this.progressProvider.percentComplete();

        int newProgressWidth = (int) (window.width() * songProgress);

        if (newProgressWidth != progressWidth || windowChanged) {
            int evenWidth = window.width() / 2 * 2;
            createTiles(newProgressWidth, evenWidth);
        }
    }

    private void createTiles(int progressWidth, int barWidth) {
        this.tiles.clear();

        this.tiles.addAll(this.createFullTiles(progressWidth, barWidth));
        this.tiles.addAll(this.createEmptyTiles(progressWidth, barWidth));
    }

    private Collection<PositionedInnerTile> createFullTiles(int progressWidth, int barWidth) {
        ProgressBarTileSet tileSet = tilesets.filled();

        List<PositionedInnerTile> tiles = new ArrayList<>();

        int middleTileCount = barWidth - (tileSet.left().size().width() + tileSet.right().size().width());

        Point placement = Point.of(-barWidth / 2, -tileSet.height());
        int goalPos = progressWidth - barWidth / 2;

        for (TilePosition tilePosition : TilePosition.values()) {
            InnerTile tile = tileSet.get(tilePosition);
            int tileCount = tilePosition == TilePosition.MIDDLE ? middleTileCount : 1;

            for (int i = 0; i < tileCount; i++) {
                boolean startsBeforeGoal = placement.x() < goalPos;
                boolean endsBeforeGoal = placement.x() + tile.size().width() < goalPos;

                if (startsBeforeGoal && endsBeforeGoal) {
                    tiles.add(tile.atPos(placement));
                } else if (startsBeforeGoal && !endsBeforeGoal) {
                    int partialWidth = goalPos - placement.x();
                    InnerTile partialTile = new InnerTile(tile.texture(), tile.textureOffset(),
                            tile.size().withX(partialWidth));

                    tiles.add(partialTile.atPos(placement));
                }

                placement = placement.offset(tile.size().width(), 0);
            }
        }

        return tiles;
    }

    private Collection<PositionedInnerTile> createEmptyTiles(int progressWidth, int barWidth) {
        ProgressBarTileSet tileSet = tilesets.empty();

        List<PositionedInnerTile> tiles = new ArrayList<>();

        int middleTileCount = (barWidth - (tileSet.left().size().width() + tileSet.right().size().width()))
                / tileSet.middle().size().width();

        Point placement = Point.of(-barWidth / 2, -tileSet.height());
        int goalPos = progressWidth - barWidth / 2;

        for (TilePosition tilePosition : TilePosition.values()) {
            InnerTile tile = tileSet.get(tilePosition);
            int tileCount = tilePosition == TilePosition.MIDDLE ? middleTileCount : 1;

            for (int i = 0; i < tileCount; i++) {
                boolean startsBeforeGoal = placement.x() < goalPos;
                boolean endsBeforeGoal = placement.x() + tile.size().width() < goalPos;

                if (!startsBeforeGoal && !endsBeforeGoal) {
                    tiles.add(tile.atPos(placement));
                } else if (startsBeforeGoal && !endsBeforeGoal) {
                    int partialWidth = placement.x() + tile.size().width() - goalPos;
                    Point offset = Point.ZERO.withX(tile.size().width() - partialWidth);
                    InnerTile partialTile = new InnerTile(tile.texture(), tile.textureOffset().offset(offset),
                            tile.size().withX(partialWidth));

                    KitTunes.LOGGER.info("Partial width: " + partialWidth + ", Offset: " + offset);

                    tiles.add(partialTile.atPos(placement.offset(offset)));
                }

                placement = placement.offset(tile.size().width(), 0);
            }
        }

        return tiles;
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        for (PositionedInnerTile tile : tiles) {
            tile.draw(gui);
        }
    }

    @Override
    public void onClick(Point mousePos, MouseButton button) {
        // TODO: Add clicking support to change the position in the song maybe???
        // That's probably hard...
    }

    @Override
    public boolean isWithinBounds(Point mousePos) {
        return false;
    }

    @Override
    public Optional<AlignmentStrategy> alignmentMethod() {
        return Optional.of(Alignment.CENTER_BOTTOM);
    }
}
