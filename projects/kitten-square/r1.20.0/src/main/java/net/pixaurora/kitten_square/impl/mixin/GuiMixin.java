package net.pixaurora.kitten_square.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;

@Mixin(Gui.class)
public class GuiMixin {
    // Because we want to show our own notification, we cancel this one so it
    // doesn't show twice.
    @Inject(method = "setNowPlaying", at = @At("HEAD"), cancellable = true)
    private void cancelNowPlayingNotification(Component description, CallbackInfo cInfo) {
        cInfo.cancel();
    }
}
