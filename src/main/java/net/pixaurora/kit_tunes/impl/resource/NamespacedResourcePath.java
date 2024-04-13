package net.pixaurora.kit_tunes.impl.resource;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import net.pixaurora.kit_tunes.impl.config.DualSerializer;

public class NamespacedResourcePath {
	public static final String NAMESPACE_SEPARATOR = ":";
	public static final String DIR_SEPARATOR = "/";

	private final String namespace;
	private final ResourcePath path;

	public NamespacedResourcePath(String namespace, ResourcePath path) {
		this.namespace = namespace;
		this.path = path;
	}

	public static NamespacedResourcePath fromString(String text) throws JsonParseException {
		String[] parts = text.split(NAMESPACE_SEPARATOR, 2);

		if (parts.length != 2) {
			throw new JsonParseException("Namespaced Resource Paths require a " + NAMESPACE_SEPARATOR + " to be present!");
		}

		return new NamespacedResourcePath(parts[0], ResourcePath.fromString(parts[1], DIR_SEPARATOR));
	}

	public String namespace() {
		return this.namespace;
	}

	public String path() {
		return this.path.toString(DIR_SEPARATOR);
	}

	public String toString() {
		return this.namespace + NAMESPACE_SEPARATOR + this.path();
	}

	public static final class Serializer implements DualSerializer<NamespacedResourcePath> {
		@Override
		public JsonElement serialize(NamespacedResourcePath item, Type _type, JsonSerializationContext context) {
			return new JsonPrimitive(item.toString());
		}

		@Override
		public NamespacedResourcePath deserialize(JsonElement data, Type _type, JsonDeserializationContext context) throws JsonParseException {
			return NamespacedResourcePath.fromString(data.getAsJsonPrimitive().getAsString());
		}
	}
}
