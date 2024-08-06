package net.pixaurora.kitten_heart.impl.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.pixaurora.kitten_heart.impl.Constants;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.error.UnhandledKitTunesException;

public class HttpHelper {
    @SuppressWarnings("resource")
    public static InputStream get(String endpoint, Map<String, String> queryParameters)
            throws UnhandledKitTunesException {
        return UnhandledKitTunesException.runOrThrow(() -> handleRequest("GET", endpoint, queryParameters));
    }

    @SuppressWarnings("resource")
    public static InputStream post(String endpoint, Map<String, String> queryParameters)
            throws UnhandledKitTunesException {
        return UnhandledKitTunesException.runOrThrow(() -> handleRequest("POST", endpoint, queryParameters));
    }

    public static Map<String, String> defaultHeaders() {
        Map<String, String> headers = new HashMap<>();

        headers.put("User-Agent", "Kit Tunes/" + Constants.MOD_VERSION + " (+" + Constants.HOMEPAGE + ")");

        return headers;
    }

    public static void logResponse(InputStream data) throws IOException {
        StringBuilder builder = new StringBuilder();

        int c = data.read();
        while (c != -1) {
            builder.append((char) c);

            c = data.read();
        }

        KitTunes.LOGGER.info("Received response: " + builder.toString());
    }

    private static InputStream handleRequest(String method, String endpoint, Map<String, String> queryParameters)
            throws IOException {
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

        connection.getContentLength();

        if (connection.getResponseCode() == 200) {
            return connection.getInputStream();
        } else {
            return connection.getErrorStream();
        }
    }

    private static String createQuery(Map<String, String> queryParameters) {
        List<String> query = new ArrayList<>(queryParameters.size());

        for (Map.Entry<String, String> parameter : queryParameters.entrySet()) {
            try {
                query.add(parameter.getKey() + "="
                        + URLEncoder.encode(parameter.getValue(), StandardCharsets.UTF_8.toString()));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Should never happen, URL encoding is hard-coded.", e);
            }
        }

        return query.size() == 0 ? "" : "?" + String.join("&", query);
    }

    public static boolean isUnreserved(char value) {
        return ('A' <= value && value <= 'Z') || ('a' <= value && value <= 'z') || ('0' <= value && value <= '9')
                || value == '-' || value == '_' || value == '.' || value == '~';
    }

    private static HttpURLConnection narrowConnection(URLConnection connection) throws UnhandledKitTunesException {
        if (connection instanceof HttpURLConnection) {
            return (HttpURLConnection) connection;
        } else {
            throw new UnhandledKitTunesException(
                    "URL Connection must be of type HttpURLConnection, not `" + connection.getClass().getName() + "`!");
        }
    }
}
