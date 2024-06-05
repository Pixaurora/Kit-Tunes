package net.pixaurora.kit_tunes.impl.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

import net.pixaurora.kit_tunes.impl.error.UnhandledScrobblerException;

public class HttpHelper {
	public static InputStream get(String endpoint, Map<String, String> queryParameters) throws UnhandledScrobblerException {
		return handleRequest("GET", endpoint, queryParameters);
	}

	public static InputStream post(String endpoint, Map<String, String> queryParameters) throws UnhandledScrobblerException {
		return handleRequest("POST", endpoint, queryParameters);
	}

	public static Map<String, String> defaultHeaders() {
		Map<String, String> headers = new HashMap<>();

		headers.put("User-Agent", "Kit Tunes/0.1a (+https://github.com/Pixaurora/Kit-Tunes)");

		return headers;
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

	private static String createQuery(Map<String, String> queryParameters) {
		List<String> query = new ArrayList<>(queryParameters.size());

		for (var parameter : queryParameters.entrySet()) {
			query.add(parameter.getKey() + "=" + encode(parameter.getValue()));
		}

		return String.join("&", query);
	}

	private static HttpURLConnection narrowConnection(URLConnection connection) throws UnhandledScrobblerException {
		if (connection instanceof HttpURLConnection) {
			return (HttpURLConnection) connection;
		} else {
			throw new UnhandledScrobblerException("URL Connection must be of type HttpURLConnection, not `" + connection.getClass().getName() + "`!");
		}
	}

	private static InputStream handleRequest(String method, String endpoint, Map<String, String> queryParameters) throws UnhandledScrobblerException {
		return UnhandledScrobblerException.sendErrorsUpstream(() -> handleRequestWithIOException(method, endpoint, queryParameters));
	}

	private static InputStream handleRequestWithIOException(String method, String endpoint, Map<String, String> queryParameters) throws IOException {
		URL url = URI.create(endpoint + "?" + createQuery(queryParameters)).toURL();

		HttpURLConnection connection = narrowConnection(url.openConnection());

		connection.setRequestMethod(method);

		// Set headers
		defaultHeaders().forEach((key, value) -> connection.setRequestProperty(key, value));
		connection.setDoOutput(true);
		connection.setFixedLengthStreamingMode(0);

		connection.connect();

		return connection.getInputStream();
	}
}
