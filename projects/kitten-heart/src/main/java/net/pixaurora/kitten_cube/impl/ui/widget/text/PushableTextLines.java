package net.pixaurora.kitten_cube.impl.ui.widget.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import net.pixaurora.kitten_heart.impl.util.Pair;

public class PushableTextLines implements Widget {
    private static final TextLinesBackground BODY_BACKGROUND = new TextLinesBackground(Point.of(2, 2),
            new InnerTileGrid(
                    GuiTexture.of(KitTunes.resource("textures/gui/sprites/widget/textbox/regular.png"),
                            Size.of(10, 11)),
                    Point.of(2, 1), Size.of(6, 9)),
            Size.of(2, 0));

    private static final TextLinesBackground TITLE_BACKGROUND = new TextLinesBackground(Point.of(5, 4),
            new InnerTileGrid(
                    GuiTexture.of(KitTunes.resource("textures/gui/sprites/widget/textbox/title.png"),
                            Size.of(16, 15)),
                    Point.of(4, 3), Size.of(6, 9)),
            Size.of(4, 2));

    private final Optional<TextLinesBackground> backgroundType;
    private final List<Component> lines;
    private Color color;

    private ImmutableTextDisplay display;

    public PushableTextLines(Optional<TextLinesBackground> backgroundType) {
        this.backgroundType = backgroundType;
        this.lines = new ArrayList<>();
        this.color = Color.WHITE;
        this.resetDisplay();
    }

    public static PushableTextLines body() {
        return new PushableTextLines(Optional.of(BODY_BACKGROUND));
    }

    public static PushableTextLines title() {
        return new PushableTextLines(Optional.of(TITLE_BACKGROUND));
    }

    public void clear() {
        this.lines.clear();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void push(Component text) {
        this.lines.add(text);

        this.resetDisplay();
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        for (PositionedInnerTile tile : this.display.tiles) {
            tile.draw(gui);
        }

        gui.drawTextBox(this.display.textBox);
    }

    @Override
    public Size size() {
        return this.display.size;
    }

    @Override
    public void onClick(Point mousePos, MouseButton button) {
    }

    @Override
    public boolean isWithinBounds(Point mousePos) {
        return false;
    }

    private void resetDisplay() {
        Point textStart;
        if (this.backgroundType.isPresent()) {
            textStart = this.backgroundType.get().textStart();
        } else {
            textStart = Point.ZERO;
        }

        TextBox textBox = TextBox.of(this.lines, color, textStart);
        Size displaySize = textBox.size().offset(textStart);

        List<PositionedInnerTile> tiles;
        if (this.backgroundType.isPresent()) {
            TextLinesBackground background = this.backgroundType.get();

            Pair<List<PositionedInnerTile>, Size> tilesAndSize = background
                    .grid()
                    .tilesAndSize(Point.ZERO, displaySize.offset(background.bottomRightPadding()));

            tiles = tilesAndSize.first();
            displaySize = tilesAndSize.second();
        } else {
            tiles = new ArrayList<>();
        }

        this.display = new ImmutableTextDisplay(textBox, tiles, displaySize);
    }

    private static class ImmutableTextDisplay {
        private final TextBox textBox;
        private final List<PositionedInnerTile> tiles;
        private final Size size;

        ImmutableTextDisplay(TextBox textBox, List<PositionedInnerTile> tiles, Size size) {
            this.textBox = textBox;
            this.tiles = tiles;
            this.size = size;
        }
    }
}
