package net.pixaurora.kit_tunes.impl.network;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

import net.pixaurora.kit_tunes.impl.error.UnhandledScrobblerException;

public class HttpHelper {
	private static final HttpClient CLIENT = HttpClient.newBuilder()
		.followRedirects(Redirect.NORMAL)
		.build();

	public static HttpRequest.Builder startRequest(URI endpoint) {
		return HttpRequest.newBuilder(endpoint)
			.header("User-Agent", "Kit Tunes/0.1a (+https://github.com/Pixaurora/Kit-Tunes)");
	}

	public static boolean isUnreserved(char value) {
		return ('A' <= value && value <= 'Z') ||
			('a' <= value && value <= 'z') ||
			('0' <= value && value <= '9') ||
			value == '-' || value == '_' || value == '.' || value == '~';
	}

	public static String encode(String value) {
		String encodedValue = "";

		for (char character : value.toCharArray()) {
			if (character == ' ') {
				encodedValue += "+";
			} else if (isUnreserved(character)) {
				encodedValue += character;
			} else {
				encodedValue += "%" + HexFormat.of().formatHex(new byte[]{(byte) character});
			}
		}

		return encodedValue;
	}

	public static String createQuery(Map<String, String> queryParameters) {
		List<String> query = new ArrayList<>(queryParameters.size());

		for (var parameter : queryParameters.entrySet()) {
			query.add(parameter.getKey() + "=" + encode(parameter.getValue()));
		}

		return String.join("&", query);
	}

	public static HttpResponse<InputStream> get(String endpoint, Map<String, String> queryParameters) throws UnhandledScrobblerException {
		return UnhandledScrobblerException.sendErrorsUpstream(() ->
			CLIENT.send(
				startRequest(URI.create(endpoint + "?" + createQuery(queryParameters)))
					.build(),
				HttpResponse.BodyHandlers.ofInputStream()
		));
	}

	public static HttpResponse<InputStream> post(String endpoint, Map<String, String> queryParameters) throws UnhandledScrobblerException {
		return UnhandledScrobblerException.sendErrorsUpstream(() ->
			CLIENT.send(
				startRequest(URI.create(endpoint + "?" + createQuery(queryParameters)))
					.POST(BodyPublishers.noBody())
					.build(),
				HttpResponse.BodyHandlers.ofInputStream()
		));
	}
}
