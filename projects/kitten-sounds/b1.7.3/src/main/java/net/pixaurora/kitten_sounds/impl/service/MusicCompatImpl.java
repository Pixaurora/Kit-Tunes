package net.pixaurora.kitten_sounds.impl.service;

import net.minecraft.client.Minecraft;
import net.pixaurora.kitten_heart.impl.service.MusicCompat;

public class MusicCompatImpl implements MusicCompat {
    private static final long MILLIS_PER_TICK = 50;

    public static long ticksToMillis(int ticks) {
        return MILLIS_PER_TICK * ticks;
    }

    @Override
    public long millisToNextSong() {
        return ticksToMillis(Minecraft.INSTANCE.soundSystem.musicCooldown);
    }
}
