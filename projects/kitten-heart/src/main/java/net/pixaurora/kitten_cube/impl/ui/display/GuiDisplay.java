package net.pixaurora.kitten_cube.impl.ui.display;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;

public interface GuiDisplay {
    public void drawTexture(ResourcePath path, int width, int height, int x, int y);

    public void drawGuiTextureSubsection(ResourcePath path, int width, int height, int x, int y, int subsectionWidth,
            int subsectionHeight, int offsetX, int offsetY);

    public void drawText(Component text, Color color, int x, int y, boolean shadowed);

    public void drawTextBox(TextBox box, Alignment alignment, Size window);

    public default void drawTextBox(TextBox box) {
        this.drawTextBox(box, Alignment.TOP_LEFT, Size.ZERO);
    }

    public default void drawTexture(ResourcePath path, Size size, Point pos) {
        this.drawTexture(path, size.width(), size.height(), pos.x(), pos.y());
    }

    public default void drawGuiTextureSubsection(ResourcePath path, Size size, Point pos, Size subsection,
            Point offset) {
        this.drawGuiTextureSubsection(path, size.width(), size.height(), pos.x(), pos.y(), subsection.width(),
                subsection.height(), offset.x(), offset.y());
    }

    public default void drawText(Component text, Color color, Point pos, boolean shadowed) {
        this.drawText(text, color, pos.x(), pos.y(), shadowed);
    }

    public default void drawText(Component text, Color color, Point pos) {
        this.drawText(text, color, pos, true);
    }

    public default void draw(Texture texture, Point pos) {
        this.drawTexture(texture.path(), texture.size(), pos);
    }

    public default void drawGui(GuiTexture texture, Point pos) {
        this.drawGuiTextureSubsection(texture.path(), texture.size(), pos, texture.size(), Point.ZERO);
    }

    public default void drawGui(GuiTexture texture, Point pos, Size subsection, Point offset) {
        this.drawGuiTextureSubsection(texture.path(), texture.size(), pos, subsection, offset);
    }

    public default int alignX(int x, int y) {
        return x;
    }

    public default int alignY(int x, int y) {
        return y;
    }
}
