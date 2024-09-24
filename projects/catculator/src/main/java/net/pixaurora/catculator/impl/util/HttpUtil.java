package net.pixaurora.catculator.impl.util;

import org.quiltmc.loader.api.ModMetadata;
import org.quiltmc.loader.api.QuiltLoader;

import net.pixaurora.catculator.impl.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class HttpUtil {
    private static final Duration TIMEOUT = Duration.of(2, ChronoUnit.MINUTES);

    public static void download(URL url, Path into) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout((int) TIMEOUT.toMillis());
        connection.setRequestProperty("User-Agent", buildUserAgent());

        connection.connect();
        int status = connection.getResponseCode();

        if (status != 200) {
            throw new IOException("Library download error: " + status);
        }

        int size;
        String length = connection.getHeaderField("Content-Length");

        try {
            size = Integer.parseInt(length);
        } catch (NumberFormatException e) {
            throw new IOException("Received invalid Content-Length header!");
        }

        try (InputStream stream = connection.getInputStream()) {
            int input;
            int index = 0;

            byte[] data = new byte[size];

            while ((input = stream.read()) != -1) {
                data[index] = (byte) input;
                index++;
            }

            Files.write(into, data);
        }
    }

    private static String buildUserAgent() {
        ModMetadata metadata = QuiltLoader.getModContainer(Constants.MOD_ID).get().metadata();

        String version = metadata.version().raw();
        String homepage = metadata.getContactInfo("homepage");

        return String.format("Kit Tunes/%s (+%s)", version, homepage);
    }
}
