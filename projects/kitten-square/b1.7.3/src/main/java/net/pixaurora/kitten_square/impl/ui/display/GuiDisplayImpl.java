package net.pixaurora.kitten_square.impl.ui.display;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.vertex.BufferBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiElement;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;
import net.pixaurora.kitten_square.impl.ui.ConversionCacheImpl;
import net.pixaurora.kitten_square.impl.ui.widget.TextBoxImpl;

public class GuiDisplayImpl implements GuiDisplay {
    private final GuiElement element;
    private final ConversionCacheImpl conversions;

    public GuiDisplayImpl(GuiElement element, ConversionCacheImpl conversions) {
        this.element = element;
        this.conversions = conversions;
    }

    @Override
    public void drawTexture(ResourcePath path, int width, int height, int x, int y, double alpha) {
        this.drawGuiTextureSubsection(path, width, height, x, y, width, height, 0, 0, alpha);
    }

    @Override
    public void drawGuiTextureSubsection(ResourcePath path, int width, int height, int x, int y, int subsectionWidth,
            int subsectionHeight, int offsetX, int offsetY, double alpha) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.INSTANCE.textureManager.load(this.conversions.convert(path)));

        float u = offsetX;
        float v = offsetY;

        float invertedTexWidth = 1.0f / width;
        float invertedTexHeight = 1.0f / height;

        BufferBuilder bufferBuilder = BufferBuilder.INSTANCE;
        bufferBuilder.start();
        bufferBuilder.color(255, 255, 255, (int) (255.0 * alpha));
        bufferBuilder.vertex(x, y + subsectionHeight, 0.0, u * invertedTexWidth,
                (v + (float) subsectionHeight) * invertedTexHeight);
        bufferBuilder.vertex(x + subsectionWidth, y + subsectionHeight, 0.0,
                (u + (float) subsectionWidth) * invertedTexWidth,
                (v + (float) subsectionHeight) * invertedTexHeight);
        bufferBuilder.vertex(x + subsectionWidth, y, 0.0, (u + (float) subsectionWidth) * invertedTexWidth,
                v * invertedTexHeight);
        bufferBuilder.vertex(x, y, 0.0, u * invertedTexWidth, v * invertedTexHeight);
        bufferBuilder.end();
    }

    @Override
    public void drawText(Component text, Color color, int x, int y, boolean shadowed) {
        element.drawString(Minecraft.INSTANCE.textRenderer, this.conversions.convert(text), x, y, color.hex());
    }

    @Override
    public void drawTextBox(TextBox textBox, Alignment alignment, Size window) {
        if (!(textBox instanceof TextBoxImpl)) {
            throw new UnsupportedOperationException("Unsupported instance of textbox");
        }

        TextBoxImpl impl = (TextBoxImpl) textBox;

        int x = alignment.alignX(impl.startPos.x(), impl.startPos.y(), window);
        int y = alignment.alignY(impl.startPos.x(), impl.startPos.y(), window);

        for (String line : impl.lines) {
            this.element.drawString(Minecraft.INSTANCE.textRenderer, line, x, y, impl.color.hex());

            y += MinecraftClient.textHeight();
        }
    }
}
