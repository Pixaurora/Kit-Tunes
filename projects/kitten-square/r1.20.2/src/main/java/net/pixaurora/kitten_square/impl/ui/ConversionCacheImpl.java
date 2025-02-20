package net.pixaurora.kitten_square.impl.ui;

import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.ConversionCache;
import net.pixaurora.kitten_square.impl.service.UICompatImpl;

public class ConversionCacheImpl extends ConversionCache<ResourceLocation, net.minecraft.network.chat.Component> {
    @Override
    protected ResourceLocation resourceToMinecraftType(ResourcePath path) {
        return UICompatImpl.internalToMinecraftType(path);
    }

    @Override
    protected net.minecraft.network.chat.Component componentToMinecraftType(
            net.pixaurora.kitten_cube.impl.text.Component component) {
        return UICompatImpl.internalToMinecraftType(component);
    }

}
