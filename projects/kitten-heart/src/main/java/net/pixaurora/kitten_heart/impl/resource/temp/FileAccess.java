package net.pixaurora.kitten_heart.impl.resource.temp;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.quiltmc.loader.api.QuiltLoader;

public interface FileAccess extends Closeable {
    public static final Path TEMP_FILE_DIR = QuiltLoader.getCacheDir().resolve("kit_tunes").resolve("tmp");

    public static FileAccess create(Path staticFile) {
        return new PermanentFileAccess(staticFile);
    }

    public static FileAccess create(InputStream data) throws IOException {
        Files.createDirectories(TEMP_FILE_DIR);

        Path tempFile = Files.createTempFile(TEMP_FILE_DIR, null, null);

        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            int nextByte = data.read();
            while (nextByte != -1) {
                writer.write((byte) nextByte);
            }
        }

        return new TempFileAcces(tempFile);
    }

    public Path path();
}
