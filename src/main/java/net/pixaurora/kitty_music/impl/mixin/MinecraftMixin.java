package net.pixaurora.kitty_music.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.pixaurora.kitty_music.impl.MusicListener;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	public void addMusicListener(CallbackInfo ci) {
		Minecraft client = (Minecraft)(Object) this;

		client.getSoundManager().addListener(new MusicListener(client));
	}
}
