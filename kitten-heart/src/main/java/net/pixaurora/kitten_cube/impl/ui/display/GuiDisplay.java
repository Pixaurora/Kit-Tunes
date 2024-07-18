package net.pixaurora.kitten_cube.impl.ui.display;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;

public interface GuiDisplay {
    public void drawTexture(ResourcePath path, Size size, Point pos);

    public void drawGuiTextureSubsection(ResourcePath path, Size size, Point pos, Size subsection, Point offset);

    public void drawText(Component text, Color color, Point pos, boolean shadowed);

    public void drawTextBox(TextBox textBox);

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
}
