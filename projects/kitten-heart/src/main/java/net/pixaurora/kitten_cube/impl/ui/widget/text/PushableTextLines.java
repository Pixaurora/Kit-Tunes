package net.pixaurora.kitten_cube.impl.ui.widget.text;

import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public class PushableTextLines implements Widget {
    private final Point startPos;
    private final List<PositionedText> lines;

    public PushableTextLines(Point startPos) {
        this.lines = new ArrayList<>();
        this.startPos = startPos;
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
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
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
}
