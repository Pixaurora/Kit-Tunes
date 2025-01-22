package net.pixaurora.kitten_cube.impl.ui.display;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;

public class WrappedGuiDisplay implements GuiDisplay {
    private final GuiDisplay parent;

    WrappedGuiDisplay(GuiDisplay parent) {
        this.parent = parent;
    }

    public GuiDisplay parent() {
        return this.parent;
    }

    @Override
    public void drawTexture(ResourcePath path, int width, int height, int x, int y, double alpha) {
        this.parent.drawTexture(path, width, height, x, y, alpha);
    }

    @Override
    public void drawGuiTextureSubsection(ResourcePath path, int width, int height, int x, int y, int subsectionWidth,
            int subsectionHeight, int offsetX, int offsetY, double alpha) {
        this.parent.drawGuiTextureSubsection(path, width, height, x, y, subsectionWidth, subsectionHeight, offsetX,
                offsetY, alpha);
    }

    @Override
    public void drawText(Component text, Color color, int x, int y, boolean shadowed) {
        this.parent.drawText(text, color, x, y, shadowed);
    }

    @Override
    public void drawTextBox(TextBox box, Alignment alignment, Size window) {
        this.parent.drawTextBox(box, alignment, window);
    }

    @Override
    public int alignX(int x, int y) {
        return this.parent.alignX(x, y);
    }

    @Override
    public int alignY(int x, int y) {
        return this.parent.alignY(x, y);
    }
}
