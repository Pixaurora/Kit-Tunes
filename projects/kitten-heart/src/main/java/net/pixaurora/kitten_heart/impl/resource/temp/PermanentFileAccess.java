package net.pixaurora.kitten_heart.impl.resource.temp;

import java.io.IOException;
import java.nio.file.Path;

public class PermanentFileAccess implements FileAccess {
    private final Path file;

    public PermanentFileAccess(Path file) {
        this.file = file;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public Path path() {
        return this.file;
    }
}
