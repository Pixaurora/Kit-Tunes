package net.pixaurora.kit_tunes.impl.ui;

import net.pixaurora.kit_tunes.impl.ui.math.Point;

public interface Drawable<T> {
    public void draw(T handle, GuiDisplay gui, Point mousePos);
}
