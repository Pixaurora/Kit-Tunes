package net.pixaurora.kit_tunes.impl.ui;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;
import net.pixaurora.kit_tunes.impl.ui.texture.GuiTexture;
import net.pixaurora.kit_tunes.impl.ui.texture.Texture;

public interface GuiDisplay {
    public void drawTexture(ResourcePath path, Size size, Point pos);

    public void drawGuiTextureSubsection(ResourcePath path, Size size, Point pos, Size subsection, Point offset);

    public void drawText(Component text, Color color, Point pos, boolean shadowed);

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
