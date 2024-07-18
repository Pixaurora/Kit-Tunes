package net.pixaurora.kitten_star.impl;

import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathImpl;

public class SoundEventsUtils {
    public static ResourcePath minecraftTypeToInternalType(ResourceLocation identifier) {
        return new ResourcePathImpl(identifier.getNamespace(), identifier.getPath());
    }
}
