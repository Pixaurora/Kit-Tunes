package net.pixaurora.kit_tunes.impl.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;

import net.pixaurora.kit_tunes.impl.KitTunes;

public class ConfigManager<T> {
	private final Path savePath;

	private final Codec<T> configCodec;
	private final Supplier<T> defaultConfig;

	private final Gson serializer;

	private T config;

	public ConfigManager(Path savePath, Codec<T> codec, Supplier<T> defaults) {
		this.savePath = savePath;

		this.configCodec = codec;
		this.defaultConfig = defaults;

		this.serializer = new GsonBuilder()
			.setPrettyPrinting()
			.disableHtmlEscaping()
			.create();

		this.config = this.createConfig();
	}

	public T get() {
		return this.config;
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

	private T load() throws IOException {
		JsonElement configData = JsonParser.parseString(Files.readString(this.savePath));

		return this.configCodec.decode(JsonOps.INSTANCE, configData).getOrThrow(false, RuntimeException::new).getFirst();
	}

	public boolean save(T config) {
		JsonElement result = this.configCodec.encodeStart(JsonOps.INSTANCE, config).getOrThrow(true, RuntimeException::new);

		try {
			Files.writeString(this.savePath, this.serializer.toJson(result));

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
