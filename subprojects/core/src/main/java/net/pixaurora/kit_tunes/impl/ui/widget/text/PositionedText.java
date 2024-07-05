package net.pixaurora.kit_tunes.impl.ui.widget.text;

import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;

public class PositionedText {
    private final Component text;
    private final Point pos;
    private final Color color;

    public PositionedText(Component text, Point pos, Color color) {
        this.text = text;
        this.pos = pos;
        this.color = color;
    }

    public Component text() {
        return this.text;
    }

    public Point pos() {
        return this.pos;
    }

    public Color color() {
        return this.color;
    }
}
