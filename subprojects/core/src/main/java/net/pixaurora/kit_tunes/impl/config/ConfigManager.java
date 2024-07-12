package net.pixaurora.kit_tunes.impl.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.gson.JsonParseException;

import net.pixaurora.kit_tunes.impl.KitTunes;

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

    public void save() {
        this.execute(this::save);
    }

    private boolean configLocationWritable() {
        Path saveDirectory = this.savePath.getParent();

        try {
            Files.createDirectories(saveDirectory);

            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    private T load() throws IOException, JsonParseException {
        BufferedReader configData = Files.newBufferedReader(this.savePath, StandardCharsets.UTF_8);

        return Serialization.serializer().fromJson(configData, this.configClass);
    }

    private boolean save(T config) {
        String result = Serialization.serializer().toJson(config, this.configClass);

        try {
            Files.write(this.savePath, result.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);

            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    private T createConfig() {
        try {
            return this.load();
        } catch (IOException exception) {
            T config = this.defaultConfig.get();

            boolean configWritten = this.configLocationWritable() && this.save(config);

            if (!configWritten) {
                KitTunes.LOGGER.warn("Default config was not written!");
            }

            return config;
        }
    }
}
