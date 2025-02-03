package net.pixaurora.kitten_square.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReceiver;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.WidgetSprites;
import net.pixaurora.kitten_square.impl.ui.widget.TextFieldImpl;

@Mixin(EditBox.class)
public class EditBoxMixin {

    /**
     * Because the field for the background is both private and static, the only way
     * to modify it in our subclass is when it is used.
     */
    @ModifyReceiver(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/WidgetSprites;get(ZZ)Lnet/minecraft/resources/ResourceLocation;"))
    private WidgetSprites replaceBackground(WidgetSprites receiver, boolean a, boolean b) {
        if ((Object) this instanceof TextFieldImpl) {
            return ((TextFieldImpl) (Object) this).background();
        } else {
            return receiver;
        }
    }
}
