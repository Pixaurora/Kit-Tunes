package net.pixaurora.kit_tunes.impl.mixin.ui;

import org.quiltmc.qsl.command.api.client.ClientCommandRegistrationCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.pixaurora.kit_tunes.impl.command.ScrobblingCommand;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	public void addMusicListener(CallbackInfo ci) {
		ClientCommandRegistrationCallback.EVENT.register(ScrobblingCommand::register);
	}
}
