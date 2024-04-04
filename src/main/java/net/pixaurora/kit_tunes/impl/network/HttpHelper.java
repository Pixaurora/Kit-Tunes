package net.pixaurora.kit_tunes.impl.network;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
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

	public static HttpResponse<InputStream> get(String endpoint, Map<String, String> queryParameters) throws UnhandledScrobblerException {
		List<String> query = new ArrayList<>(queryParameters.size());

		for (var parameter : queryParameters.entrySet()) {
			query.add(parameter.getKey() + "=" + parameter.getValue());
		}

		return UnhandledScrobblerException.sendErrorsUpstream(() ->
			CLIENT.send(
				startRequest(URI.create(endpoint + "?" + String.join("&", query)))
					.build(),
				HttpResponse.BodyHandlers.ofInputStream()
		));
	}

	public static HttpResponse<InputStream> post(String endpoint, String body) throws UnhandledScrobblerException {
		return UnhandledScrobblerException.sendErrorsUpstream(() ->
			CLIENT.send(
				startRequest(URI.create(endpoint))
					.POST(HttpRequest.BodyPublishers.ofString(body))
					.build(),
				HttpResponse.BodyHandlers.ofInputStream()
		));
	}
}
