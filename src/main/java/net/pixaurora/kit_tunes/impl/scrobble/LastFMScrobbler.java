package net.pixaurora.kit_tunes.impl.scrobble;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.network.Encryption;
import net.pixaurora.kit_tunes.impl.network.HttpHelper;
import net.pixaurora.kit_tunes.impl.network.SetupServer;

public class LastFMScrobbler implements Scrobbler {
	public static final Codec<LastFMScrobbler> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Codec.STRING.fieldOf("token").forGetter(scrobbler -> scrobbler.token)
		).apply(instance, LastFMScrobbler::new)
	);

	public static final String API_KEY = "693bf5425eb442ceaf6f627993c7918d";
	public static final String SHARED_SECRET = "9920afdfeba7ec08b3dc966f9d603cd5";

	public static final String ROOT_API_URL = "http://ws.audioscrobbler.com/2.0/";
	public static final String SETUP_URL = "https://last.fm/api/auth?api_key=" + API_KEY;

	public static final ScrobblerType<LastFMScrobbler> TYPE = new ScrobblerType<>("lastfm", CODEC, SETUP_URL, LastFMScrobbler::setup);

	private final String token;
	@Nullable private LastFMSession session;

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

	Map<String, String> addSignature(Map<String, String> parameters) {
		var sortedParameters = new ArrayList<>(parameters.entrySet());
		sortedParameters.sort(Comparator.comparing(entry -> entry.getKey()));

		String regularSignature = "";
		for (var parameter : sortedParameters) {
			regularSignature += parameter.getKey() + parameter.getValue();
		}

		regularSignature += SHARED_SECRET;

		parameters = new HashMap<>(parameters);
		parameters.put("api_sig", Encryption.signMd5(regularSignature));

		return parameters;
	}

	private LastFMSession createSession() throws IOException, InterruptedException {
		HttpResponse<String> response = HttpHelper.get(
			ROOT_API_URL,
			addSignature(
				Map.of(
					"method", "auth.getSession",
					"api_key", API_KEY,
					"token", this.token
				)
			)
		);

		String body = response.body();

		KitTunes.LOGGER.info(body);

		return new LastFMSession("Placeholder", "placeholder", 0);
	}

	private LastFMSession session() throws IOException, InterruptedException {
		if (session == null) {
			this.session = this.createSession();
		}

		return this.session;
	}

	@Override
	public String username() throws IOException, InterruptedException {
		return this.session().name();
	}


	@Override
	public ScrobblerType<?> type() {
		return TYPE;
	}

	public static record LastFMSession(String name, String key, int subscriber) {
	}
}
