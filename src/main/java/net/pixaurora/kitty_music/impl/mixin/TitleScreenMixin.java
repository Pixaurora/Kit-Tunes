package net.pixaurora.kitty_music.impl.mixin;

import net.minecraft.client.gui.screens.TitleScreen;
import net.pixaurora.kitty_music.impl.KittyMusic;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Inject(method = "init", at = @At("TAIL"))
	public void onInit(CallbackInfo ci) {
		KittyMusic.LOGGER.info("Hello, World!");
	}
}
