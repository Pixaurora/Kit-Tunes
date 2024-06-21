package net.pixaurora.kit_tunes.impl.gui;

import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.KitTunesUIImpl;
import net.pixaurora.kit_tunes.impl.ui.ConversionCache;

public class ConversionCacheImpl extends ConversionCache<ResourceLocation, net.minecraft.network.chat.Component> {
	@Override
	protected ResourceLocation resourceToMinecraftType(ResourcePath path) {
		return KitTunesUIImpl.resourceToMinecraftType(path);
	}

	@Override
	protected net.minecraft.network.chat.Component componentToMinecraftType(net.pixaurora.kit_tunes.impl.ui.Component component) {
		return KitTunesUIImpl.componentToMinecraftType(component);
	}

}
