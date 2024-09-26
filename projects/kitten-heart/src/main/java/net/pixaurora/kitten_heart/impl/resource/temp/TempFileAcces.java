package net.pixaurora.kitten_heart.impl.resource.temp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TempFileAcces implements FileAccess {
    private final Path file;

    public TempFileAcces(Path file) {
        this.file = file;
    }

    @Override
    public void close() throws IOException {
        Files.delete(file);
    }

    @Override
    public Path path() {
        return this.file;
    }
}
