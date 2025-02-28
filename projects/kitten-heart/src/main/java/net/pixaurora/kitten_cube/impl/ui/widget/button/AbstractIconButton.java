package net.pixaurora.kitten_cube.impl.ui.widget.button;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.sound.Sound;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.RectangularSurface;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.WidgetSurface;

public abstract class AbstractIconButton implements Button {
    private static final ButtonBackground BACKGROUND = ButtonBackground.NEUTRAL_SQUARE;
    private static final Size SIZE = Size.of(20, 20);
    private static final Point ICON_POS = SIZE.centerWithinSelf(Size.of(16, 16));

    private final WidgetSurface surface;

    public AbstractIconButton() {
        this.surface = RectangularSurface.of(SIZE);
    }

    protected abstract GuiTexture icon();

    protected abstract void onClick(Point mousePos);

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        gui.drawGui(BACKGROUND.texture(this.isDisabled(), this.isWithinBounds(mousePos)), Point.ZERO);
        gui.drawGui(this.icon(), ICON_POS);
    }

    @Override
    public void onClick(Point mousePos, MouseButton button) {
        if (button == MouseButton.PRIMARY) {
            this.onClick(mousePos);
        }

        MinecraftClient.playSound(Sound.BUTTON_CLICK);
    }

    @Override
    public WidgetSurface surface() {
        return this.surface;
    }
}
