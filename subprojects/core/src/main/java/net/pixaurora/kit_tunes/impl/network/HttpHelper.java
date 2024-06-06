package net.pixaurora.kit_tunes.impl.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.pixaurora.kit_tunes.impl.Constants;
import net.pixaurora.kit_tunes.impl.error.UnhandledScrobblerException;

public class HttpHelper {
	public static InputStream get(String endpoint, Map<String, String> queryParameters) throws UnhandledScrobblerException {
		return UnhandledScrobblerException.runOrThrow(() -> handleRequest("GET", endpoint, queryParameters));
	}

	public static InputStream post(String endpoint, Map<String, String> queryParameters) throws UnhandledScrobblerException {
		return UnhandledScrobblerException.runOrThrow(() -> handleRequest("POST", endpoint, queryParameters));
	}

	public static Map<String, String> defaultHeaders() {
		Map<String, String> headers = new HashMap<>();

		headers.put("User-Agent", "Kit Tunes/" + Constants.MOD_VERSION + " (+" + Constants.HOMEPAGE + ")");

		return headers;
	}

	private static InputStream handleRequest(String method, String endpoint, Map<String, String> queryParameters) throws IOException {
		URL url = new URL(endpoint + createQuery(queryParameters));

		HttpURLConnection connection = narrowConnection(url.openConnection());

		connection.setRequestMethod(method);

		// Set headers
		defaultHeaders().forEach((key, value) -> connection.setRequestProperty(key, value));
		connection.setRequestProperty("Content-Length", "0");

		if (method == "POST") { // Only if POSTing, set Content-Length
			connection.setDoOutput(true);
			connection.setFixedLengthStreamingMode(0);
		}

		if (connection.getResponseCode() == 200) {
			return connection.getInputStream();
		} else {
			return connection.getErrorStream();
		}
	}

	private static String createQuery(Map<String, String> queryParameters) {
		List<String> query = new ArrayList<>(queryParameters.size());

		for (var parameter : queryParameters.entrySet()) {
			query.add(parameter.getKey() + "=" + encode(parameter.getValue()));
		}

		return query.size() == 0 ? "" : "?" + String.join("&", query);
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

	public static boolean isUnreserved(char value) {
		return ('A' <= value && value <= 'Z') ||
			('a' <= value && value <= 'z') ||
			('0' <= value && value <= '9') ||
			value == '-' || value == '_' || value == '.' || value == '~';
	}

	private static HttpURLConnection narrowConnection(URLConnection connection) throws UnhandledScrobblerException {
		if (connection instanceof HttpURLConnection) {
			return (HttpURLConnection) connection;
		} else {
			throw new UnhandledScrobblerException("URL Connection must be of type HttpURLConnection, not `" + connection.getClass().getName() + "`!");
		}
	}
}
