package net.pixaurora.kit_tunes.impl.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.pixaurora.kit_tunes.impl.scrobble.Scrobbler;

public class Serialization {
	private static Gson SERIALIZER = createSerializer();

	private static final Gson createSerializer() {
		return new GsonBuilder()
			.setPrettyPrinting()
			.disableHtmlEscaping()
			.registerTypeAdapter(ScrobblerCache.class, new ScrobblerCache.Serializer())
			.registerTypeAdapter(Scrobbler.class, Scrobbler.TYPES.itemSerialzier())
			.create();
	}

	public static Gson serializer() {
		return SERIALIZER;
	}
}
