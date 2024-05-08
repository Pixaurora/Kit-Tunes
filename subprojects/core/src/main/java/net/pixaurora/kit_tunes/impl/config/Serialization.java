package net.pixaurora.kit_tunes.impl.config;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import net.pixaurora.kit_tunes.api.resource.NamespacedResourcePath;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.scrobble.Scrobbler;

public class Serialization {
	private static Gson SERIALIZER = createSerializer();

	private static final Gson createSerializer() {
		return new GsonBuilder()
			.setPrettyPrinting()
			.disableHtmlEscaping()
			.registerTypeAdapter(ScrobblerCache.class, new ScrobblerCache.Serializer())
			.registerTypeAdapter(Scrobbler.class, Scrobbler.TYPES.itemSerialzier())
			.registerTypeAdapter(ResourcePath.class, new ResourcePathSerializer())
			.registerTypeAdapter(NamespacedResourcePath.class, new NamedResourcePathSerializer())
			.create();
	}

	public static Gson serializer() {
		return SERIALIZER;
	}

	public static final class ResourcePathSerializer implements DualSerializer<ResourcePath> {
		@Override
		public JsonElement serialize(ResourcePath item, Type _type, JsonSerializationContext context) {
			return new JsonPrimitive(item.toString());
		}

		@Override
		public ResourcePath deserialize(JsonElement data, Type _type, JsonDeserializationContext context) throws JsonParseException {
			return ResourcePath.fromString(data.getAsJsonPrimitive().getAsString());
		}
	}

	public static final class NamedResourcePathSerializer implements DualSerializer<NamespacedResourcePath> {
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
