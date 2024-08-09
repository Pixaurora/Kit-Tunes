package net.pixaurora.kitten_thoughts.impl.util;

import net.pixaurora.kitten_thoughts.impl.Constants;
import net.pixaurora.kitten_thoughts.impl.error.LibraryLoadError;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class NativeUtil {
    private static boolean isLoaded = false;

    private static final String METADATA = "/kitten_thoughts.natives.properties";
    private static final String BASE_URL = "https://files.lostluma.net/kitten-thoughts-jni/" + Constants.NATIVES_VERSION + "/";

    public static void load() throws LibraryLoadError {
        if (isLoaded) {
            return;
        }

        try {
            load0();
            isLoaded = true;
        } catch (IOException e) {
            throw new LibraryLoadError(e);
        }
    }

    private static void load0() throws IOException, LibraryLoadError {
        String directory = System.getProperty(Constants.NATIVES_DIRECTORY_PROPERTY);

        if (directory != null) {
            loadDevLibrary(directory);
        } else {
            loadProdLibrary();
        }
    }

    private static void loadDevLibrary(String directory) throws LibraryLoadError {
        String name = getDevLibraryName();
        Path path = Paths.get(directory).resolve(name);

        try {
            System.load(path.toAbsolutePath().toString());
        } catch (UnsatisfiedLinkError e) {
            throw new LibraryLoadError(e);
        }
    }

    private static String getDevLibraryName() {
        String name = System.getProperty("os.name").toLowerCase();

        if (name.contains("win")) {
            return "kitten_thoughts.dll";
        } else if (name.contains("mac")) {
            return "libkitten_thoughts.dylib";
        } else {
            return "libkitten_thoughts.so";
        }
    }

    private static void loadProdLibrary() throws IOException, LibraryLoadError {
        Properties properties = new Properties();

        try (InputStream stream = NativeUtil.class.getResourceAsStream(METADATA)) {
            if (stream == null) {
                throw new LibraryLoadError("Failed to read native library info.");
            }

            properties.load(stream);
        }

        String base = getArch() + "." + getName();

        if (properties.getProperty(base + ".name") == null) {
            throw new LibraryLoadError("No library found for " + getArch() + " " + getName());
        }

        String name = properties.getProperty(base + ".name");
        String hash = properties.getProperty(base + ".hash");

        Path path = Constants.NATIVES_CACHE_DIR.resolve(name);

        if (!isLibraryValid(path, hash)) {
            Files.createDirectories(path.getParent());
            HttpUtil.download(new URL(BASE_URL + name), path);

            if (!isLibraryValid(path, hash)) {
                throw new LibraryLoadError("Native library could not be validated.");
            }
        }

        try {
            System.load(path.toAbsolutePath().toString());
        } catch (UnsatisfiedLinkError e) {
            throw new LibraryLoadError(e);
        }
    }

    private static String getArch() {
        String arch = System.getProperty("os.arch");

        // MacOS
        if (arch.equals("x86_64")) {
            arch = "amd64";
        }

        return arch;
    }

    private static String getName() {
        String name = System.getProperty("os.name").toLowerCase();

        if (name.contains("win")) {
            return "windows";
        } else if (name.contains("mac")) {
            return "macos";
        } else {
            return "linux";
        }
    }

    private static boolean isLibraryValid(Path path, String hash) throws IOException {
        return Files.exists(path) && hash.equals(CryptoUtil.sha512(path));
    }
}
