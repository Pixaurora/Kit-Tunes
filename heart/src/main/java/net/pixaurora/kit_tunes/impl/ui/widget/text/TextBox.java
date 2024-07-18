package net.pixaurora.kit_tunes.impl.ui.widget.text;

import java.util.List;

import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;

public interface TextBox {
    public static TextBox of(List<Component> lines, Color color, int maxLineLength, Point pos) {
        return KitTunes.UI_LAYER.createTextbox(lines, color, maxLineLength, pos);
    }

    public Size size();
}
