package net.pixaurora.kit_tunes.impl.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;

public class GuiDisplayImpl implements GuiDisplay {
    private final GuiGraphics graphics;

    private final ConversionCacheImpl conversions;

    public GuiDisplayImpl(GuiGraphics graphics, ConversionCacheImpl conversions) {
        this.graphics = graphics;
        this.conversions = conversions;
    }

    @Override
    public void drawTexture(ResourcePath path, Size size, Point pos) {
        int width = size.width();
        int height = size.height();

        this.graphics.blit(conversions.convert(path), pos.x(), pos.y(), 0, 0.0F, 0.0F, width, height, width, height);
    }

    @Override
    public void drawGuiTexture(ResourcePath path, Size size, Point pos) {
        this.graphics.blitSprite(conversions.convert(path), pos.x(), pos.y(), size.width(), size.height());
    }

    @SuppressWarnings("resource")
    @Override
    public void drawText(Component text, Color color, Point pos) {
        this.graphics.drawString(Minecraft.getInstance().font, conversions.convert(text), pos.x(), pos.y(),
                color.hex());
    }
}
