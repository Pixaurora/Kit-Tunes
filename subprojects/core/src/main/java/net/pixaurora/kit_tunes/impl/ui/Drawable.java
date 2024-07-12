package net.pixaurora.kit_tunes.impl.ui;

import net.pixaurora.kit_tunes.impl.ui.display.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;

public interface Drawable {
    public void draw(GuiDisplay gui, Point mousePos);
}
