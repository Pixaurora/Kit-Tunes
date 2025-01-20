package net.pixaurora.kitten_cube.impl.ui.widget.text;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;
import net.pixaurora.kitten_cube.impl.ui.widget.event.WindowUpdateEvent;
import net.pixaurora.kitten_heart.impl.KitTunes;

public interface TextField extends Widget {
    public static TextField regular() {
        return KitTunes.UI_LAYER.newTextField(TextFieldBackground.REGULAR_BACKGROUND);
    }

    @Override
    default void onWindowUpdate(WindowUpdateEvent event) {
        KitTunes.UI_LAYER.addTextField(event.screen(), this);
    }

    public String input();

    public void setPos(int x, int y);

    @Override
    default void draw(GuiDisplay gui, Point mousePos) {
        this.setPos(gui.alignX(0, 0), gui.alignY(0, 0));
    }

    @Override
    default void onClick(Point mousePos, MouseButton button) {
        return;
    }

    @Override
    default boolean isWithinBounds(Point mousePos) {
        return false;
    }
}
