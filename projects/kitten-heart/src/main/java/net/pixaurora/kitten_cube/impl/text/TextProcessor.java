package net.pixaurora.kitten_cube.impl.text;

import java.util.List;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;

public interface TextProcessor {
    public int textHeight();

    public int textWidth(Component text);

    public TextBox createTextbox(List<Component> lines, Color color, int maxLineLength, Point pos);

    public default Size textSize(Component text) {
        return Size.of(this.textWidth(text), this.textHeight());
    }

    public default Size textSize(Component... lines) {
        int width = 0;

        for (Component line : lines) {
            width = Math.max(width, this.textWidth(line));
        }

        return Size.of(width, this.textHeight() * lines.length);
    }
}
