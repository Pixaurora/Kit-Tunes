package net.pixaurora.kit_tunes.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.gui.KitTunesToastImpl;
import net.pixaurora.kit_tunes.impl.resource.ResourcePathUtils;
import net.pixaurora.kit_tunes.impl.service.KitTunesMinecraftUICompat;
import net.pixaurora.kit_tunes.impl.ui.Component;
import net.pixaurora.kit_tunes.impl.ui.toast.KitTunesToastData;

public class KitTunesUIImpl implements KitTunesMinecraftUICompat {
	public static ResourceLocation resourceToMinecraftType(ResourcePath path) {
		return new ResourceLocation(path.namespace(), path.path());
	}

	public static ResourceLocation resourceToMinecraftGuiSprite(ResourcePath path) {
		return resourceToMinecraftType(ResourcePathUtils.stripSuffixAndPrefix("textures/gui/sprites/", ".png", path).get());
	}

	public static MutableComponent componentToMinecraftType(Component component) {
		if (component instanceof FakeComponent) {
			return ((FakeComponent) component).parent;
		} else {
			throw new RuntimeException("Internal component is of an unconvertable type `" + component.getClass().getName() + "`!");
		}
	}

	@Override
	public void sendToast(KitTunesToastData toastData) {
		Minecraft client = Minecraft.getInstance();
		Toast toast = new KitTunesToastImpl(client.font, toastData);

		client.getToasts().addToast(toast);
	}

	@Override
	public Component translatable(String key) {
		return new FakeComponent(net.minecraft.network.chat.Component.translatable(key));
	}

	@Override
	public Component translatableWithFallback(String key, String defaultText) {
		return new FakeComponent(net.minecraft.network.chat.Component.translatableWithFallback(key, defaultText));
	}

	@Override
	public Component literal(String text) {
		return new FakeComponent(net.minecraft.network.chat.Component.literal(text));
	}
}