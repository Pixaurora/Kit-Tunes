package net.pixaurora.kit_tunes.impl.network;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import net.pixaurora.kit_tunes.impl.error.UnhandledScrobblerException;

public class HttpHelper {
	private static final CloseableHttpClient CLIENT = HttpClientBuilder.create()
		.setDefaultHeaders(defaultHeaders())
		.build();

	public static List<Header> defaultHeaders() {
		ArrayList<Header> headers = new ArrayList<>();

		headers.add(new BasicHeader("User-Agent", "Kit Tunes/0.1a (+https://github.com/Pixaurora/Kit-Tunes)"));

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

	public static String createQuery(Map<String, String> queryParameters) {
		List<String> query = new ArrayList<>(queryParameters.size());

		for (var parameter : queryParameters.entrySet()) {
			query.add(parameter.getKey() + "=" + encode(parameter.getValue()));
		}

		return String.join("&", query);
	}

	private static InputStream handleRequest(HttpUriRequest request) {
		String rawBody = UnhandledScrobblerException.sendErrorsUpstream(
			() -> CLIENT.execute(request, new BasicResponseHandler())
		);

		return new ByteArrayInputStream(rawBody.getBytes());
	}

	public static InputStream get(String endpoint, Map<String, String> queryParameters) throws UnhandledScrobblerException {
		return handleRequest(new HttpGet(URI.create(endpoint + "?" + createQuery(queryParameters))));
	}

	public static InputStream post(String endpoint, Map<String, String> queryParameters) throws UnhandledScrobblerException {
		return handleRequest(new HttpPost(URI.create(endpoint + "?" + createQuery(queryParameters))));
	}
}
