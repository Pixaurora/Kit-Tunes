package net.pixaurora.kitten_square.impl.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import net.pixaurora.kitten_square.impl.ui.toast.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Inject(method = "setValue(Lnet/minecraft/client/options/GameOptions$Option;I)V", at = @At(value = "TAIL"))
    public void onGuiScaleChanged(CallbackInfo cInfo) {
        ToastManager.INSTANCE.onWindowUpdate(ToastManager.scaledWindow(Minecraft.INSTANCE.width, Minecraft.INSTANCE.height));
    }
}
