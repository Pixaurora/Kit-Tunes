package net.pixaurora.kitten_heart.impl.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.quiltmc.loader.api.QuiltLoader;

import com.google.gson.JsonParseException;

import net.pixaurora.kitten_heart.impl.Constants;
import net.pixaurora.kitten_heart.impl.KitTunes;

public class ConfigManager<T> {
    private final Path savePath;

    private final Class<T> configClass;
    private final Supplier<T> defaultConfig;

    private T config;

    public ConfigManager(Path savePath, Class<T> configClass, Supplier<T> defaults) {
        this.savePath = savePath;

        this.configClass = configClass;
        this.defaultConfig = defaults;

        this.config = this.createConfig();
    }

    public synchronized void execute(Consumer<T> task) {
        task.accept(this.config);
    }

    public synchronized void save() throws IOException {
        this.saveAtomically(config);
    }

    private void saveAtomically(T config) throws IOException {
        String configData = Serialization.serializer().toJson(config, this.configClass);

        Path cache = QuiltLoader.getCacheDir();
        Path tempSavePath = Files.createTempFile(cache, Constants.MOD_ID, "json");

        Files.write(tempSavePath, configData.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        move(tempSavePath, this.savePath);
    }

    private void move(Path from, Path to) throws IOException {
        try {
            Files.move(from, to, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException | UnsupportedOperationException e) {
            Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private T createConfig() {
        try {
            return this.load();
        } catch (IOException exception) {
            KitTunes.LOGGER.info("Failed to load config! Creating default one.");
        }

        T config = this.defaultConfig.get();

        try {
            Path saveDirectory = this.savePath.getParent();
            Files.createDirectories(saveDirectory);

            this.saveAtomically(config);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config", e);
        }

        return config;
    }

    private T load() throws IOException, JsonParseException {
        BufferedReader configData = Files.newBufferedReader(this.savePath, StandardCharsets.UTF_8);

        return Serialization.serializer().fromJson(configData, this.configClass);
    }
}
