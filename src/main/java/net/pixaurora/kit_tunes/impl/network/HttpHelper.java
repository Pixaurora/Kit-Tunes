package net.pixaurora.kit_tunes.impl.network;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpHelper {
	private static final HttpClient CLIENT = HttpClient.newBuilder()
		.followRedirects(Redirect.NORMAL)
		.build();

	public static HttpRequest.Builder startRequest(URI endpoint) {
		return HttpRequest.newBuilder(endpoint);
	}

	public static HttpResponse<String> get(String endpoint, Map<String, String> queryParameters) throws IOException, InterruptedException {
		List<String> query = new ArrayList<>(queryParameters.size());

		for (var parameter : queryParameters.entrySet()) {
			query.add(parameter.getKey() + "=" + parameter.getValue());
		}

		return CLIENT.send(
			startRequest(URI.create(endpoint + "?" + String.join("&", query)))
				.build(),
			HttpResponse.BodyHandlers.ofString()
		);
	}
}
