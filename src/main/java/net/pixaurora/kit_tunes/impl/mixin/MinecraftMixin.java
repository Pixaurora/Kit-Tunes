package net.pixaurora.kit_tunes.impl.mixin;

import org.quiltmc.qsl.command.api.client.ClientCommandRegistrationCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.pixaurora.kit_tunes.impl.MusicListener;
import net.pixaurora.kit_tunes.impl.command.ScrobblingCommand;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	public void addMusicListener(CallbackInfo ci) {
		Minecraft client = (Minecraft)(Object) this;

		client.getSoundManager().addListener(new MusicListener(client));

		ClientCommandRegistrationCallback.EVENT.register(ScrobblingCommand::register);
	}
}
