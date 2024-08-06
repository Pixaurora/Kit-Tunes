package net.pixaurora.kitten_cube.impl.ui.display;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
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
    public void drawTexture(ResourcePath path, Size size, Point pos) {
        this.parent.drawTexture(path, size, pos);
    }

    @Override
    public void drawGuiTextureSubsection(ResourcePath path, Size size, Point pos, Size subsection, Point offset) {
        this.parent.drawGuiTextureSubsection(path, size, pos, subsection, offset);
    }

    @Override
    public void drawText(Component text, Color color, Point pos, boolean shadowed) {
        this.parent.drawText(text, color, pos, shadowed);
    }

    @Override
    public void drawTextBox(TextBox box) {
        this.parent.drawTextBox(box);
    }
}
