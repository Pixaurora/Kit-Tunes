package net.pixaurora.kitten_cube.impl.ui;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;

public interface Drawable {
    public void draw(GuiDisplay gui, Point mousePos);
}
