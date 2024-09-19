package net.pixaurora.kitten_cube.impl.ui.widget;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.RectangularSurface;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.WidgetSurface;

public class StaticTexture implements BasicWidget {
    private final Texture texture;
    private final RectangularSurface surface;

    private StaticTexture(Texture icon, RectangularSurface surface) {
        this.texture = icon;
        this.surface = surface;
    }

    public StaticTexture(Texture icon, Point pos) {
        this(icon, RectangularSurface.of(pos, icon.size()));
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        gui.draw(this.texture, this.surface.startPos());
    }

    public WidgetSurface surface() {
        return this.surface;
    }

    @Override
    public void onClick(Point mousePos, MouseButton button) {
    }
}
