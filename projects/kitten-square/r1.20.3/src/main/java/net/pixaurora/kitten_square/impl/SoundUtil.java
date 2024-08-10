package net.pixaurora.kitten_square.impl;

import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.pixaurora.kitten_cube.impl.ui.sound.Sound;

public class SoundUtil {
    public static SoundInstance soundFromInternalID(Sound sound) {
        switch (sound) {
        case BUTTON_CLICK:
            return SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F);
        default:
            throw new RuntimeException("Sound " + sound.name() + " was not mapped!");
        }
    }
}
