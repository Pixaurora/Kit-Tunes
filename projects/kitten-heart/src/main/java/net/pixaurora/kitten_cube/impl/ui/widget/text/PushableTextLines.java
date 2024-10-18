package net.pixaurora.kitten_cube.impl.ui.widget.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.tile.InnerTileGrid;
import net.pixaurora.kitten_cube.impl.ui.tile.PositionedInnerTile;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;
import net.pixaurora.kitten_heart.impl.KitTunes;

public class PushableTextLines implements Widget {
    private static final TextLinesBackground REGULAR_BACKGROUND = new TextLinesBackground(Point.of(7, 5),
            new InnerTileGrid(
                    GuiTexture.of(KitTunes.resource("textures/gui/sprites/widget/textbox.png"), Size.of(20, 17)),
                    Point.of(6, 4), Size.of(6, 9)),
            Size.of(6, 2));

    private final Point startPos;
    private final Optional<TextLinesBackground> backgroundType;

    private final List<PositionedText> lines;
    private final List<PositionedInnerTile> backgroundTiles;

    public PushableTextLines(Point startPos, Optional<TextLinesBackground> backgroundType) {
        this.startPos = startPos;
        this.backgroundType = backgroundType;
        this.lines = new ArrayList<>();
        this.backgroundTiles = new ArrayList<>();
    }

    public static PushableTextLines regular(Point startPos) {
        return new PushableTextLines(startPos, Optional.of(REGULAR_BACKGROUND));
    }

    private int height() {
        return this.lines.size() * MinecraftClient.textHeight();
    }

    public Point endPos() {
        return startPos.offset(0, this.height());
    }

    public void clear() {
        this.lines.clear();
    }

    public void push(Component text, Color color) {
        Point newLinePos = MinecraftClient.textSize(text).centerHorizontally(startPos).offset(0, this.height());
        this.lines.add(new PositionedText(text, color, newLinePos));

        this.updateBackground();
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        for (PositionedInnerTile tile : this.backgroundTiles) {
            tile.draw(gui);
        }

        for (PositionedText line : this.lines) {
            gui.drawText(line.text(), line.color(), line.pos());
        }
    }

    @Override
    public void onClick(Point mousePos, MouseButton button) {
    }

    @Override
    public boolean isWithinBounds(Point mousePos) {
        return false;
    }

    private void updateBackground() {
        this.backgroundTiles.clear();

        TextLinesBackground background = this.backgroundType.get();

        InnerTileGrid grid = background.grid();
        Point startPos = this.topLeftLinePoint().offset(background.textStart().scaledBy(-1));
        Size totalSize = this.linesSize().offset(background.textStart()).offset(background.bottomRightPadding());

        this.backgroundTiles.addAll(grid.tilesAndSize(startPos, totalSize).first());
    }

    private Size linesSize() {
        return MinecraftClient
                .textSize(this.lines.stream().map(PositionedText::text).toArray(size -> new Component[size]));
    }

    private Point topLeftLinePoint() {
        Iterator<PositionedText> lines = this.lines.iterator();

        PositionedText line = lines.next();
        Point topLeftPoint = line.pos();

        while (lines.hasNext()) {
            line = lines.next();

            Point linePos = line.pos();
            topLeftPoint = Point.of(Math.min(topLeftPoint.x(), linePos.x()), Math.min(topLeftPoint.y(), linePos.y()));
        }

        return topLeftPoint;
    }
}
