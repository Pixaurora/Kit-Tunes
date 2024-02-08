package net.pixaurora.kit_tunes.impl.scrobble;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class LastFMScrobbler implements Scrobbler {
	public static final Codec<LastFMScrobbler> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Codec.STRING.fieldOf("token").forGetter(scrobbler -> scrobbler.token)
		).apply(instance, LastFMScrobbler::new)
	);

	public static final String API_KEY = "693bf5425eb442ceaf6f627993c7918d";
	public static final String SHARED_SECRET = "9920afdfeba7ec08b3dc966f9d603cd5";

	public static final String SETUP_URL = "https://www.last.fm/api/auth?api_key=" + API_KEY;

	public static final ScrobblerType<LastFMScrobbler> TYPE = new ScrobblerType<>("lastfm", CODEC, SETUP_URL, LastFMScrobbler::setup);

	private final String token;

	public LastFMScrobbler(String token) {
		this.token = token;
	}

	public static CompletableFuture<LastFMScrobbler> setup() {
		try {
			return SetupServer.create("token=").awaitedToken().thenApply(LastFMScrobbler::new);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public ScrobblerType<?> type() {
		return TYPE;
	}
}
