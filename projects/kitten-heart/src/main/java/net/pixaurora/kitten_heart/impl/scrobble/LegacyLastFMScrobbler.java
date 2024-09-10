package net.pixaurora.kitten_heart.impl.scrobble;

public class LegacyLastFMScrobbler extends LastFMScrobbler {
    public static final String API_KEY = "693bf5425eb442ceaf6f627993c7918d";
    public static final String SHARED_SECRET = "9920afdfeba7ec08b3dc966f9d603cd5";

    public static final ScrobblerType<LegacyLastFMScrobbler> TYPE = new ScrobblerType<>("last.fm",
            LegacyLastFMScrobbler.class, null, null);

    public LegacyLastFMScrobbler(LastFMSession session) {
        super(session);
    }

    @Override
    protected String apiKey() {
        return API_KEY;
    }

    @Override
    protected String sharedSecret() {
        return SHARED_SECRET;
    }
}
