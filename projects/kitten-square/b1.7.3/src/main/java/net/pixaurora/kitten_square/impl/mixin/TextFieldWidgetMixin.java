package net.pixaurora.kitten_square.impl.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.Minecraft;
import net.pixaurora.kitten_square.impl.ui.widget.TextFieldImpl;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.TextRenderer;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextFieldWidget.class)
public class TextFieldWidgetMixin {
    @Final
    @Shadow
    private TextRenderer textRenderer;

    /**
     * Because the background is drawn by using calls to draw squares, we must first cancel those two calls if it's our class.
     */
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;fill(IIIII)V", ordinal = 0))
    private void removeVanillaBorder(TextFieldWidget instance, int i, int j, int k, int l, int m, Operation<Void> original) {
        if (!((Object) this instanceof TextFieldImpl)) {
            original.call(instance, i, j, k, l, m);
        }
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;fill(IIIII)V", ordinal = 1))
    private void removeVanillaBackground(TextFieldWidget instance, int i, int j, int k, int l, int m, Operation<Void> original) {
        if (!((Object) this instanceof TextFieldImpl)) {
            original.call(instance, i, j, k, l, m);
        }
    }

    /**
     * Then, we can draw our own background instead.
     */
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;fill(IIIII)V", ordinal = 1))
    private void drawCustomAppearance(CallbackInfo cInfo) {
        if (!((Object) this instanceof TextFieldImpl)) {
            return;
        }

        TextFieldImpl instance = (TextFieldImpl) (Object) this;

        String background = instance.focused ? instance.background().highlighted() : instance.background().normal();

        this.drawCustomBackground(background, instance.x, instance.y, instance.size().width(),
                instance.size().height());

        if (!instance.focused && instance.input().isEmpty()) {
            this.showHint(instance);
        }
    }

    private void drawCustomBackground(String texture, int x, int y, int width, int height) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.INSTANCE.textureManager.load(texture));

        int subsectionWidth = width;
        int subsectionHeight = height;

        float u = 0;
        float v = 0;

        float invertedTexWidth = 1.0f / width;
        float invertedTexHeight = 1.0f / height;

        BufferBuilder bufferBuilder = BufferBuilder.INSTANCE;
        bufferBuilder.start();
        bufferBuilder.color(1.0f, 1.0f, 1.0f, 1.0f);
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

    /*
     * This version also didn't let you change the text color, so I also do that
     */
    @ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args="intValue=14737632"))
    private int editingColor(int original) {
        if (!((Object) this instanceof TextFieldImpl)) {
            return original;
        } else {
            return ((TextFieldImpl) (Object) this).background().colors().typed().hex();
        }
    }

    private void showHint(TextFieldImpl instance) {
        int x = instance.x + 4;
        int y = instance.y + (instance.size().height() - 8) / 2;
        int color = instance.background().colors().hint().hex();

        this.textRenderer.drawWithShadow(instance.hint(), x, y, color);
    }
}
