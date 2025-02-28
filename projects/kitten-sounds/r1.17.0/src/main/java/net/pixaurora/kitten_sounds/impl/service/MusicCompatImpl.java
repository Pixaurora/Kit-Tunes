package net.pixaurora.kitten_sounds.impl.service;

import net.minecraft.client.Minecraft;
import net.pixaurora.kitten_heart.impl.service.MusicCompat;
import net.pixaurora.kitten_sounds.impl.mixin.MusicManagerAccessor;

public class MusicCompatImpl implements MusicCompat {
    private static final long MILLIS_PER_TICK = 50;

    private final Minecraft client = Minecraft.getInstance();

    public static long ticksToMillis(long ticks) {
        return MILLIS_PER_TICK * ticks;
    }

    @Override
    public long millisToNextSong() {
        int nextSongDelay = ((MusicManagerAccessor) this.client.getMusicManager()).getNextSongDelay();
        return ticksToMillis(nextSongDelay);
    }
}
