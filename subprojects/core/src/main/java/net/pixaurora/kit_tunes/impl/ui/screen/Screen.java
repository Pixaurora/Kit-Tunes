package net.pixaurora.kit_tunes.impl.ui.screen;

import net.pixaurora.kit_tunes.impl.ui.Drawable;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public interface Screen extends Drawable {
    public void init(Size window);

    public void handleClick(Point mousePos, int button);
}
