package net.pixaurora.kit_tunes.impl;

import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.resource.ResourcePathImpl;

public class KitTunesSoundEventsUtils {
    public static ResourcePath minecraftTypeToInternalType(ResourceLocation identifier) {
        return new ResourcePathImpl(identifier.getNamespace(), identifier.getPath());
    }
}
