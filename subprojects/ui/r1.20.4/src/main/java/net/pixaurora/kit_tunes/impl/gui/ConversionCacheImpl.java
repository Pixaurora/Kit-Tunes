package net.pixaurora.kit_tunes.impl.gui;

import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.UICompatImpl;
import net.pixaurora.kit_tunes.impl.ui.ConversionCache;

public class ConversionCacheImpl extends ConversionCache<ResourceLocation, net.minecraft.network.chat.Component> {
    @Override
    protected ResourceLocation resourceToMinecraftType(ResourcePath path) {
        return UICompatImpl.internalToMinecraftType(path);
    }

    @Override
    protected net.minecraft.network.chat.Component componentToMinecraftType(
            net.pixaurora.kit_tunes.impl.ui.text.Component component) {
        return UICompatImpl.internalToMinecraftType(component);
    }

}
