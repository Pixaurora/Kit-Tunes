package net.pixaurora.kit_tunes.impl.ui.widget.text;

import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;

public class PositionedText {
    private final Component text;
    private final Color color;
    private final Point pos;

    public PositionedText(Component text, Color color, Point pos) {
        this.text = text;
        this.color = color;
        this.pos = pos;
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
