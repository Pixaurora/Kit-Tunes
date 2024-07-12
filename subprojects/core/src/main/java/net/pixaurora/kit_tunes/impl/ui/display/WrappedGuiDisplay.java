package net.pixaurora.kit_tunes.impl.ui.display;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;
import net.pixaurora.kit_tunes.impl.ui.widget.text.TextBox;

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
        this.drawText(text, color, pos, shadowed);
    }

    @Override
    public void drawTextBox(TextBox box) {
        this.drawTextBox(box);
    }
}
