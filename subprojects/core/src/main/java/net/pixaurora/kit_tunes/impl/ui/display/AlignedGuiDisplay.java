package net.pixaurora.kit_tunes.impl.ui.display;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.screen.align.PointManager;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;

public class AlignedGuiDisplay extends WrappedGuiDisplay {
    private final PointManager pointAligner;

    public AlignedGuiDisplay(GuiDisplay parent, PointManager autoAligner) {
        super(parent);
        this.pointAligner = autoAligner;
    }

    private Point align(Point pos) {
        return this.pointAligner.align(pos);
    }

    @Override
    public void drawTexture(ResourcePath path, Size size, Point pos) {
        super.drawTexture(path, size, this.align(pos));
    }

    @Override
    public void drawGuiTextureSubsection(ResourcePath path, Size size, Point pos, Size subsection, Point offset) {
        super.drawGuiTextureSubsection(path, size, this.align(pos), subsection, offset);
    }

    @Override
    public void drawText(Component text, Color color, Point pos, boolean shadowed) {
        super.drawText(text, color, this.align(pos), shadowed);
    }
}
