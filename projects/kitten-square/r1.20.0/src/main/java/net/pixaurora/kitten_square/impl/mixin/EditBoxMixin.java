package net.pixaurora.kitten_square.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kitten_square.impl.ui.widget.TextFieldImpl;

@Mixin(EditBox.class)
public class EditBoxMixin {

    /**
     * Because the background is drawn by using calls to draw squares, we must first cancel those two calls if it's our class.
     */
    @WrapOperation(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private void removeVanillaBackground(GuiGraphics instance, int i, int j, int k, int l, int m, Operation<Void> original) {
        if (!((Object) this instanceof TextFieldImpl)) {
            original.call(instance, i, j, k, l, m);
        }
    }

    /**
     * Then, we can draw our own instead after.
     */
    @Inject(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V", ordinal = 0))
    private void drawCustomBackground(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo cInfo) {
        if (!((Object) this instanceof TextFieldImpl)) {
            return;
        }

        TextFieldImpl instance = (TextFieldImpl) (Object) this;

        ResourceLocation background = instance.isFocused() ? instance.background().highlighted() : instance.background().normal();

        guiGraphics.blit(background, instance.getX(), instance.getY(), 0, 0, instance.getWidth(), instance.getHeight(), instance.getWidth(), instance.getHeight());
    }
}
