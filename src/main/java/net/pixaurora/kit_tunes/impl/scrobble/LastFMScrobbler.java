package net.pixaurora.kit_tunes.impl.scrobble;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.pixaurora.kit_tunes.impl.network.Encryption;
import net.pixaurora.kit_tunes.impl.network.HttpHelper;
import net.pixaurora.kit_tunes.impl.network.ParsingException;
import net.pixaurora.kit_tunes.impl.network.XMLHelper;

public class LastFMScrobbler implements Scrobbler {
	public static final Codec<LastFMScrobbler> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			LastFMSession.CODEC.fieldOf("session").forGetter(scrobbler -> scrobbler.session)
		).apply(instance, LastFMScrobbler::new)
	);

	public static final String API_KEY = "693bf5425eb442ceaf6f627993c7918d";
	public static final String SHARED_SECRET = "9920afdfeba7ec08b3dc966f9d603cd5";

	public static final String ROOT_API_URL = "http://ws.audioscrobbler.com/2.0/";
	public static final String SETUP_URL = "https://last.fm/api/auth?api_key=" + API_KEY;

	public static final ScrobblerType<LastFMScrobbler> TYPE = new ScrobblerType<>("lastfm", CODEC, SETUP_URL, "token=", LastFMScrobbler::setup);

	private final LastFMSession session;

	public LastFMScrobbler(LastFMSession session) {
		this.session = session;
	}

	public static LastFMScrobbler setup(String token) throws IOException, InterruptedException, ParsingException {
		return new LastFMScrobbler(createSession(token));
	}

	private static Map<String, String> addSignature(Map<String, String> parameters) {
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

	private static LastFMSession createSession(String token) throws IOException, InterruptedException, ParsingException {
		HttpResponse<InputStream> response = HttpHelper.get(
			ROOT_API_URL,
			addSignature(
				Map.of(
					"method", "auth.getSession",
					"api_key", API_KEY,
					"token", token
				)
			)
		);

		Document body = XMLHelper.getDocument(response.body());

		Node root = XMLHelper.requireChild("lfm", body);

		return LastFMSession.fromXML("session", root);
	}

	@Override
	public String username() throws IOException, InterruptedException {
		return this.session.name();
	}

	@Override
	public ScrobblerType<?> type() {
		return TYPE;
	}

	public static record LastFMSession(String name, String key, int subscriber) {
		public static final Codec<LastFMSession> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				Codec.STRING.fieldOf("name").forGetter(LastFMSession::name),
				Codec.STRING.fieldOf("key").forGetter(LastFMSession::key),
				Codec.INT.fieldOf("subscriber").forGetter(LastFMSession::subscriber)
			).apply(instance, LastFMSession::new)
		);

		public static LastFMSession fromXML(String name, Node parent) throws ParsingException {
			Node session = XMLHelper.requireChild(name, parent);

			String username = XMLHelper.requireString("name", session);
			String key = XMLHelper.requireString("key", session);
			int subscriber = XMLHelper.requireInt("subscriber", session);

			return new LastFMSession(username, key, subscriber);
		}
	}
}
