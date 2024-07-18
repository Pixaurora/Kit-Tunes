package net.pixaurora.kitten_cube.impl.ui.screen;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.Drawable;

public interface Screen extends Drawable {
    public void init(Size window);

    public void handleClick(Point mousePos, int button);

    public default void onExit() {
    }

    public default void tick() {
    }
}
