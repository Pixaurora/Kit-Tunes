package net.pixaurora.kitten_square.impl.ui.display;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.util.FormattedCharSequence;
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
    private final PoseStack poseStack;

    private final ConversionCacheImpl conversions;

    public GuiDisplayImpl(PoseStack poseStack, ConversionCacheImpl conversions) {
        this.poseStack = poseStack;
        this.conversions = conversions;
    }

    @Override
    public void drawTexture(ResourcePath path, int width, int height, int x, int y, double alpha) {
        this.drawGuiTextureSubsection(path, width, height, x, y, width, height, 0, 0, alpha);
    }

    @Override
    public void drawGuiTextureSubsection(ResourcePath path, int width, int height, int x, int y, int subsectionWidth,
            int subsectionHeight, int offsetX, int offsetY, double alpha) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, (float) alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        RenderSystem.setShaderTexture(0, conversions.convert(path));
        GuiComponent.blit(poseStack, x, y, offsetX, offsetY, subsectionWidth, subsectionHeight, width, height);
    }

    @SuppressWarnings("resource")
    @Override
    public void drawText(Component text, Color color, int x, int y, boolean shadowed) {
        GuiComponent.drawString(poseStack, Minecraft.getInstance().font, conversions.convert(text), x, y, color.hex());
    }

    @SuppressWarnings("resource")
    @Override
    public void drawTextBox(TextBox textBox, Alignment alignment, Size window) {
        if (!(textBox instanceof TextBoxImpl)) {
            throw new UnsupportedOperationException("Unsupported instance of textbox");
        }

        TextBoxImpl impl = (TextBoxImpl) textBox;

        int x = alignment.alignX(impl.startPos, window);
        int y = alignment.alignY(impl.startPos, window);

        for (FormattedCharSequence line : impl.lines) {
            GuiComponent.drawString(poseStack, Minecraft.getInstance().font, line, x, y, impl.color.hex());

            y += MinecraftClient.textHeight();
        }
    }
}
