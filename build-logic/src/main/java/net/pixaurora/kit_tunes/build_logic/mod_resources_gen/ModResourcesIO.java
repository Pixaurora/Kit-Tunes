package net.pixaurora.kit_tunes.build_logic.mod_resources_gen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModResourcesIO {
    public static void ensureHasDirectories(Path fileLocation) throws IOException {
        Files.createDirectories(fileLocation.getParent());
    }
}
