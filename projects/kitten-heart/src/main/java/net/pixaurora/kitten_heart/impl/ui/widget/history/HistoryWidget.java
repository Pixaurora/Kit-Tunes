package net.pixaurora.kitten_heart.impl.ui.widget.history;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import net.pixaurora.kit_tunes.api.music.history.ListenRecord;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.tile.PositionedInnerTile;
import net.pixaurora.kitten_cube.impl.ui.tile.TileRow;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;
import net.pixaurora.kitten_cube.impl.ui.widget.event.WindowUpdateEvent;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.listener.HistoryWidgetUpdater;
import net.pixaurora.kitten_heart.impl.ui.widget.history.HistoryTileSet.Row;

public class HistoryWidget implements Widget {
    public static final GuiTexture BACKGROUND = GuiTexture
            .of(KitTunes.resource("textures/gui/sprites/widget/music/history/background.png"), Size.of(200, 64));

    public static final HistoryTileSet TILE_SET = HistoryTileSet.of(BACKGROUND,
            new HistoryTileSet.RowData(Point.of(13, 3), Size.of(16, 16), Point.of(31, 3), Point.of(31, 12)),
            Point.of(0, 0), Size.of(200, 21),
            Point.of(0, 21), Size.of(200, 21),
            Point.of(0, 42), Size.of(200, 22));

    private final int verticalPadding;
    private final HistoryTileSet tileSet = TILE_SET;
    private final AtomicReference<View> view;
    private final AtomicReference<Size> window;

    public HistoryWidget(int verticalPadding) {
        this.verticalPadding = verticalPadding;
        this.view = new AtomicReference<>();
        this.window = new AtomicReference<>();
    }

    @Override
    public void onWindowUpdate(WindowUpdateEvent event) {
        this.window.set(event.newWindow());
        this.update();
        HistoryWidgetUpdater.LISTENING_WIDGET.set(this);
    }

    public void update() {
        this.view.set(this.initView(HistoryWidgetUpdater.recentTracks(this.maxItems())));
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        this.view.get().draw(gui, mousePos);
    }

    @Override
    public void onClick(Point mousePos, MouseButton button) {
    }

    @Override
    public boolean isWithinBounds(Point mousePos) {
        return false;
    }

    @Override
    public Size size() {
        return this.view.get().size;
    }

    private int maxItems() {
        int middleTilesHeight = this.window.get().height() - this.verticalPadding * 2
                - (this.tileSet.top().tile().height() + this.tileSet.bottom().tile().height());
        int middleEntries = middleTilesHeight / this.tileSet.middle().tile().height();

        return middleEntries + 2; // Because the top and bottom were cancelled out before dividing
    }

    private View initView(List<ListenRecord> recentMusic) {
        List<HistoryWidgetEntry> entries = new ArrayList<>();

        List<PositionedInnerTile> tiles = new ArrayList<>();
        Size size = Size.ZERO.withX(this.tileSet.width());

        // If only 1 song has been played recently, we add an extra tile so the shape
        // isn't cut off.
        if (recentMusic.size() == 1) {
            Row rowType = this.tileSet.get(TileRow.TOP);

            Point pos = size.toPoint().withX(0);

            tiles.add(rowType.tile().atPos(pos));

            size = size.offset(0, rowType.tile().height());
        }

        for (int i = 0; i < recentMusic.size(); i++) {
            ListenRecord record = recentMusic.get(recentMusic.size() - 1 - i);
            Row rowType = this.tileSet.get(TileRow.fromIndex(recentMusic, i));

            Point entryPos = size.toPoint().withX(0);

            tiles.add(rowType.tile().atPos(entryPos));
            entries.add(rowType.entryAt(entryPos, record));

            size = size.offset(0, rowType.tile().height());
        }

        return new View(tiles, entries, size);
    }

    public static class View {
        private final List<PositionedInnerTile> tiles;
        private final List<HistoryWidgetEntry> entries;
        private final Size size;

        public View(List<PositionedInnerTile> tiles, List<HistoryWidgetEntry> entries, Size size) {
            this.tiles = tiles;
            this.entries = entries;
            this.size = size;
        }

        public void draw(GuiDisplay gui, Point mousePos) {
            for (PositionedInnerTile tile : this.tiles) {
                tile.draw(gui);
            }

            for (HistoryWidgetEntry entry : this.entries) {
                entry.draw(gui, mousePos);
            }
        }
    }
}
