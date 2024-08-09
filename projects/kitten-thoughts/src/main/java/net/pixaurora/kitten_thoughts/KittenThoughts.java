package net.pixaurora.kitten_thoughts;

import java.nio.file.Path;
import java.nio.file.Paths;

public class KittenThoughts {
    public static void init() {
        Path libraryPath = Paths.get("libkitten_thoughts.so").toAbsolutePath();
        System.load(libraryPath.toString());
    }
}
