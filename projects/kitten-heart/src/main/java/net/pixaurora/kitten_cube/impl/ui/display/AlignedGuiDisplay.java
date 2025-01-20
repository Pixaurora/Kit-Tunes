package net.pixaurora.kitten_cube.impl.ui.display;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;

public class AlignedGuiDisplay extends WrappedGuiDisplay {
    private final Alignment alignment;
    private final Size window;

    public AlignedGuiDisplay(GuiDisplay parent, Alignment alignment, Size window) {
        super(parent);
        this.alignment = alignment;
        this.window = window;
    }

    @Override
    public int alignX(int x, int y) {
        return this.alignment.alignX(super.alignX(x, y), super.alignY(x, y), this.window);
    }

    @Override
    public int alignY(int x, int y) {
        return this.alignment.alignY(super.alignX(x, y), super.alignY(x, y), this.window);
    }

    @Override
    public void drawGuiTextureSubsection(ResourcePath path, int width, int height, int x, int y, int subsectionWidth,
            int subsectionHeight, int offsetX, int offsetY) {
        super.drawGuiTextureSubsection(path, width, height, this.alignX(x, y), this.alignY(x, y), subsectionWidth,
                subsectionHeight, offsetX, offsetY);
    }

    @Override
    public void drawText(Component text, Color color, int x, int y, boolean shadowed) {
        super.drawText(text, color, this.alignX(x, y), this.alignY(x, y), shadowed);
    }

    @Override
    public void drawTexture(ResourcePath path, int width, int height, int x, int y) {
        super.drawTexture(path, width, height, this.alignX(x, y), this.alignY(x, y));
    }

    @Override
    public void drawTextBox(TextBox box) {
        super.drawTextBox(box, this.alignment, this.window);
    }
}
