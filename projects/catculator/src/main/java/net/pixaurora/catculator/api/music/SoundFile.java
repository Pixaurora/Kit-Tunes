package net.pixaurora.catculator.api.music;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;

public class SoundFile {
    public static Duration parseDuration(Path path) throws IOException {
        long nanos = parseDuration0(path.toString());

        return Duration.ofNanos(nanos);
    }

    private static native long parseDuration0(String path) throws IOException;
}
