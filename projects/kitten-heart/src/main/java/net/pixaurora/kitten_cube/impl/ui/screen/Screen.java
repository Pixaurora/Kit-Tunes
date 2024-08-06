package net.pixaurora.kitten_cube.impl.ui.screen;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.Drawable;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;

public interface Screen extends Drawable {
    public void init(Size window);

    public void handleClick(Point mousePos, MouseButton button);

    public default void onExit() {
    }

    public default void tick() {
    }
}
