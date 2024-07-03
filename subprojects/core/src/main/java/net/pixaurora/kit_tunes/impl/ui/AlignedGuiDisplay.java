package net.pixaurora.kit_tunes.impl.ui;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.screen.align.PointManager;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;

public class AlignedGuiDisplay implements GuiDisplay {
    private final GuiDisplay parent;
    private final PointManager pointAligner;

    public AlignedGuiDisplay(GuiDisplay parent, PointManager autoAligner) {
        this.parent = parent;
        this.pointAligner = autoAligner;
    }

    private Point align(Point pos) {
        return this.pointAligner.align(pos);
    }

    @Override
    public void drawTexture(ResourcePath path, Size size, Point pos) {
        this.parent.drawTexture(path, size, this.align(pos));
    }

    @Override
    public void drawGuiTexture(ResourcePath path, Size size, Point pos) {
        this.parent.drawGuiTexture(path, size, this.align(pos));
    }

    @Override
    public void drawText(Component text, Color color, Point pos) {
        this.parent.drawText(text, color, this.align(pos));
    }
}
