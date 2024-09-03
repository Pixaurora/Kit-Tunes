package net.pixaurora.kitten_square.impl.ui.display;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.util.FormattedCharSequence;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
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
    public void drawTexture(ResourcePath path, Size size, Point pos) {
        int width = size.width();
        int height = size.height();

        RenderSystem.setShaderTexture(0, conversions.convert(path));
        GuiComponent.blit(poseStack, pos.x(), pos.y(), 0, 0.0F, 0.0F, width, height, width, height);
    }

    @Override
    public void drawGuiTextureSubsection(ResourcePath path, Size size, Point pos, Size subsection, Point offset) {
        RenderSystem.setShaderTexture(0, conversions.convert(path));
        GuiComponent.blit(poseStack, pos.x(), pos.y(), offset.x(), offset.y(), subsection.width(), subsection.height(), size.width(), size.height());
    }

    @SuppressWarnings("resource")
    @Override
    public void drawText(Component text, Color color, Point pos, boolean shadowed) {
        GuiComponent.drawString(poseStack, Minecraft.getInstance().font, conversions.convert(text), pos.x(), pos.y(), color.hex());
    }

    @SuppressWarnings("resource")
    @Override
    public void drawTextBox(TextBox textBox) {
        if (textBox instanceof TextBoxImpl) {
            TextBoxImpl impl = (TextBoxImpl) textBox;

            int y = impl.startPos.y();

            for (FormattedCharSequence line : impl.lines) {
                GuiComponent.drawString(poseStack, Minecraft.getInstance().font, line, impl.startPos.x(), y, impl.color.hex());

                y += MinecraftClient.textHeight();
            }
        } else {
            throw new UnsupportedOperationException("Unsupported instance of textbox");
        }
    }
}
