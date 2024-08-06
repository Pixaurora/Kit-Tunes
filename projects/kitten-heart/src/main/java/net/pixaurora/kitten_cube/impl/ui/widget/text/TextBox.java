package net.pixaurora.kitten_cube.impl.ui.widget.text;

import java.util.List;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_heart.impl.KitTunes;

public interface TextBox {
    public static TextBox of(List<Component> lines, Color color, int maxLineLength, Point pos) {
        return KitTunes.UI_LAYER.createTextbox(lines, color, maxLineLength, pos);
    }

    public Size size();
}
