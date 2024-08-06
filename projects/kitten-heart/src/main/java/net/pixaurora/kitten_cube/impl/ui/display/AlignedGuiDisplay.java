package net.pixaurora.kitten_cube.impl.ui.display;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.align.PointManager;

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
