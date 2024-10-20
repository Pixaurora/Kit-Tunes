package net.pixaurora.kitten_heart.impl.scrobble;

import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import net.pixaurora.kitten_heart.impl.error.KitTunesException;
import net.pixaurora.kitten_heart.impl.error.ScrobblerParsingException;
import net.pixaurora.kitten_heart.impl.error.UnhandledKitTunesException;
import net.pixaurora.kitten_heart.impl.network.Encryption;
import net.pixaurora.kitten_heart.impl.network.HttpHelper;
import net.pixaurora.kitten_heart.impl.network.XMLHelper;

public class LastFMScrobbler implements Scrobbler {
    public static final String API_KEY = "6f9e533b5f6631a5aa3070f5e757de8c";
    public static final String SHARED_SECRET = "97fbf9a3d76ba36dfb5a2f6c3215bf49";

    public static final String ROOT_API_URL = "http://ws.audioscrobbler.com/2.0/";
    public static final String SETUP_URL = "https://last.fm/api/auth?api_key=" + API_KEY;

    public static final ScrobblerType<LastFMScrobbler> TYPE = new ScrobblerType<>("last.fm.new", LastFMScrobbler.class,
            SETUP_URL, LastFMScrobbler::setup);

    private final LastFMSession session;

    public LastFMScrobbler(LastFMSession session) {
        this.session = session;
    }

    public static LastFMScrobbler setup(String token) throws KitTunesException {
        return new LastFMScrobbler(createSession(token));
    }

    @Override
    public String username() {
        return this.session.name;
    }

    @Override
    public void startScrobbling(ScrobbleInfo track) throws KitTunesException {
        Map<String, String> args = new HashMap<>();

        args.put("method", "track.updateNowPlaying");

        args.put("artist", track.artistTitle());
        args.put("track", track.trackTitle());
        args.put("api_key", this.apiKey());
        args.put("sk", this.session.key);

        if (track.albumTitle().isPresent()) {
            args.put("album", track.albumTitle().get());
        }

        this.handleScrobbling(this.addSignature(args));
    }

    @Override
    public void completeScrobbling(ScrobbleInfo track) throws KitTunesException {
        Map<String, String> args = new HashMap<>();

        args.put("method", "track.scrobble");

        args.put("artist", track.artistTitle());
        args.put("track", track.trackTitle());
        args.put("timestamp", String.valueOf(track.startTime().getEpochSecond()));
        args.put("api_key", this.apiKey());
        args.put("sk", this.session.key);

        Optional<String> albumTitle = track.albumTitle();
        if (albumTitle.isPresent()) {
            args.put("album", albumTitle.get());
        }

        this.handleScrobbling(this.addSignature(args));
    }

    private void handleScrobbling(Map<String, String> args) throws KitTunesException {
        InputStream responseBody = HttpHelper.post(ROOT_API_URL, args);

        UnhandledKitTunesException.runOrThrow(() -> HttpHelper.logResponse(responseBody));
    }

    private Map<String, String> addSignature(Map<String, String> parameters) {
        return addSignature(parameters, this.sharedSecret());
    }

    private static Map<String, String> addSignature(Map<String, String> parameters, String sharedSecret) {
        List<Map.Entry<String, String>> sortedParameters = parameters.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey())).collect(Collectors.toList());

        String regularSignature = "";
        for (Map.Entry<String, String> parameter : sortedParameters) {
            regularSignature += parameter.getKey() + parameter.getValue();
        }

        regularSignature += sharedSecret;

        parameters = new HashMap<>(parameters);
        parameters.put("api_sig", Encryption.signMd5(regularSignature));

        return parameters;
    }

    private static LastFMSession createSession(String token) throws ScrobblerParsingException {
        Map<String, String> args = new HashMap<>();

        args.put("method", "auth.getSession");
        args.put("api_key", API_KEY);
        args.put("token", token);

        InputStream responseBody = HttpHelper.get(ROOT_API_URL, addSignature(args, SHARED_SECRET));

        Document body = XMLHelper.getDocument(responseBody);

        Node root = XMLHelper.requireChild("lfm", body);

        return LastFMSession.fromXML("session", root);
    }

    @Override
    public ScrobblerType<?> type() {
        return TYPE;
    }

    protected String apiKey() {
        return API_KEY;
    }

    protected String sharedSecret() {
        return SHARED_SECRET;
    }

    public static class LastFMSession {
        private final String name;
        private final String key;
        @SuppressWarnings("unused")
        private final int subscriber;

        public LastFMSession(String name, String key, int subscriber) {
            this.name = name;
            this.key = key;
            this.subscriber = subscriber;
        }

        public static LastFMSession fromXML(String name, Node parent) throws ScrobblerParsingException {
            Node session = XMLHelper.requireChild(name, parent);

            String username = XMLHelper.requireString("name", session);
            String key = XMLHelper.requireString("key", session);
            int subscriber = XMLHelper.requireInt("subscriber", session);

            return new LastFMSession(username, key, subscriber);
        }
    }
}
