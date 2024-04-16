package net.pixaurora.kit_tunes.impl.resource;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import net.pixaurora.kit_tunes.impl.config.DualSerializer;

public class ResourcePath {
	public static final String DIR_SEPARATOR = ".";
	public static final String PARSING_DIR_SEPARATOR = "\\.";

	private final List<String> path;

	public ResourcePath(String... path) {
		this.path = List.of(path);
	}

	public static ResourcePath fromString(String text, String dirSeparator) {
		return new ResourcePath(text.split(dirSeparator));
	}

	public static ResourcePath fromString(String string) {
		return fromString(string, PARSING_DIR_SEPARATOR);
	}

	public String path(String dirSeparator) {
		return String.join(dirSeparator, path);
	}

	public String toString(String dirSeparator) {
		return this.path(dirSeparator);
	}

	@Override
	public int hashCode() {
		return this.path.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof ResourcePath) {
			return ((ResourcePath) other).path.equals(this.path);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return this.toString(DIR_SEPARATOR);
	}

	public static final class Serializer implements DualSerializer<ResourcePath> {
		@Override
		public JsonElement serialize(ResourcePath item, Type _type, JsonSerializationContext context) {
			return new JsonPrimitive(item.toString());
		}

		@Override
		public ResourcePath deserialize(JsonElement data, Type _type, JsonDeserializationContext context) throws JsonParseException {
			return ResourcePath.fromString(data.getAsJsonPrimitive().getAsString());
		}
	}
}
