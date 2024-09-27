package net.pixaurora.kitten_heart.impl.resource.temp;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.quiltmc.loader.api.QuiltLoader;

public interface FileAccess extends Closeable {
    public static final Path TEMP_FILE_DIR = QuiltLoader.getCacheDir().resolve("kit_tunes").resolve("tmp");

    public static FileAccess create(Path staticFile) {
        return new PermanentFileAccess(staticFile);
    }

    public static FileAccess create(InputStream data) throws IOException {
        Files.createDirectories(TEMP_FILE_DIR);
        // TODO: Make the workaround of adding `.ogg` unnecessary
        Path tempFile = Files.createTempFile(TEMP_FILE_DIR, null, ".ogg");

        Files.copy(data, tempFile, StandardCopyOption.REPLACE_EXISTING);

        return new TempFileAccess(tempFile);
    }

    public Path path();
}
