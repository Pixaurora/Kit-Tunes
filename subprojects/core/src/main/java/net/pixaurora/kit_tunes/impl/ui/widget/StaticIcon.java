package net.pixaurora.kit_tunes.impl.ui.widget;

import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.texture.Texture;
import net.pixaurora.kit_tunes.impl.ui.widget.surface.RectangularSurface;
import net.pixaurora.kit_tunes.impl.ui.widget.surface.WidgetSurface;

public class StaticIcon implements Widget {
    private final Texture icon;
    private final RectangularSurface surface;

    public StaticIcon(Texture icon, Point pos) {
        this.icon = icon;
        this.surface = RectangularSurface.of(pos, icon.size());
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        gui.drawTexture(this.icon, this.surface.startPos());
    }

    public WidgetSurface surface() {
        return this.surface;
    }

    @Override
    public void onClick(WidgetHandle handle, Point mousePos) {
    }
}
