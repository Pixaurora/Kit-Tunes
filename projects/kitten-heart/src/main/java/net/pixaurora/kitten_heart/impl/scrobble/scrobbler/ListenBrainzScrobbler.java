package net.pixaurora.kitten_heart.impl.scrobble.scrobbler;

import net.pixaurora.catculator.api.error.ClientResponseException;
import net.pixaurora.catculator.api.http.Client;
import net.pixaurora.catculator.api.http.RequestBuilder;
import net.pixaurora.catculator.api.http.Response;
import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.history.ListenRecord;
import net.pixaurora.kit_tunes.api.scrobble.ScrobblerId;
import net.pixaurora.kitten_heart.impl.Constants;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.config.Serialization;
import net.pixaurora.kitten_heart.impl.config.dispatch.DispatchType;
import net.pixaurora.kitten_heart.impl.error.KitTunesException;
import net.pixaurora.kitten_heart.impl.error.UnhandledKitTunesException;
import net.pixaurora.kitten_heart.impl.scrobble.ScrobblerType;
import net.pixaurora.kitten_heart.impl.ui.screen.scrobbler.setup.ListenBrainzSetupScreen;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.QuiltLoader;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ListenBrainzScrobbler implements Scrobbler {
    private final Session session;

    public static final String DEFAULT_INSTANCE_URL = "https://api.listenbrainz.org";

    public static final ScrobblerType<ListenBrainzScrobbler> TYPE = ScrobblerType.screenSetup(
            "listenbrainz",
            ListenBrainzScrobbler.class,
            ListenBrainzSetupScreen::new
    );

    public ListenBrainzScrobbler(String instanceUrl, String musicBrainzId, String authorizationToken) {
        this.session = new Session(instanceUrl, musicBrainzId, authorizationToken);
    }

    @Override
    public String username() {
        return this.session.musicBrainzId;
    }

    @Override
    public DispatchType<Scrobbler> type() {
        return TYPE;
    }

    @Override
    public ScrobblerId id() {
        // TODO
        return new ScrobblerId(this.session.instanceUrl + ":" + this.username(), this.type().name());
    }

    @Override
    public void startScrobbling(Client client, ListenRecord record) throws KitTunesException {
        this.submitListen(client, SubmitListensRequest.fromRecord("playing_now", record));
    }

    @Override
    public void completeScrobbling(Client client, ListenRecord record) throws KitTunesException {
        this.submitListen(client, SubmitListensRequest.fromRecord("single", record));
    }

    private void submitListen(Client client, SubmitListensRequest request) throws KitTunesException {
        String url = this.session.instanceUrl + "/1/submit-listens";
        String data = Serialization.serializer().toJson(request);

        ListenBrainzScrobbler.request(client, "POST", url, this.session.authorizationToken, data);
    }

    public static ListenBrainzScrobbler fromToken(Client client, String instanceUrl, String authorizationToken) throws KitTunesException {
        String url = instanceUrl + "/1/validate-token";

        String data = ListenBrainzScrobbler.request(client, "GET", url, authorizationToken, null);
        ValidateTokenResponse response = Serialization.serializer().fromJson(data, ValidateTokenResponse.class);

        if (!response.valid) {
            throw new UnhandledKitTunesException("Invalid Authorization Token");
        } else {
            return new ListenBrainzScrobbler(instanceUrl, response.userName, authorizationToken);
        }
    }

    private static String request(Client client, String method, String url, String authorizationToken, @Nullable String body) throws KitTunesException {
        Response response;
        RequestBuilder builder = client.request(method, url);

        if (body != null) {
            builder.body(body.getBytes(StandardCharsets.UTF_8));
        }

        builder.header("Content-Type", "application/json");
        builder.header("Authorization", "Token " + authorizationToken);

        try {
            response = builder.send();
        } catch (ClientResponseException e) {
            throw new UnhandledKitTunesException(e);
        }

        String data = new String(response.body(), StandardCharsets.UTF_8).trim();

        if (response.ok()) {
            return data;
        } else {
            throw new UnhandledKitTunesException(data);
        }
    }

    private static final class Session {
        private final String instanceUrl;

        private final String musicBrainzId;
        private final String authorizationToken;

        private Session(String instanceUrl, String musicBrainzId, String authorizationToken) {
            this.instanceUrl = instanceUrl;

            this.musicBrainzId = musicBrainzId;
            this.authorizationToken = authorizationToken;
        }
    }

    private static final class ValidateTokenResponse {
        private int code;
        private String message;
        private boolean valid;
        private @Nullable String userName;
    }

    private static final class SubmitListensRequest {
        private final String listenType;
        private final List<Track> payload;

        private SubmitListensRequest(String listenType, List<Track> tracks) {
            this.listenType = listenType;
            this.payload = tracks;
        }

        private static SubmitListensRequest fromRecord(String listenType, ListenRecord record) {
            boolean withTimestamp = !Objects.equals(listenType, "playing_now");
            return new SubmitListensRequest(listenType, Collections.singletonList(Track.fromRecord(record, withTimestamp)));
        }
    }

    private static final class Track {
        private final @Nullable Long listenedAt;
        private final TrackMetadata trackMetadata;

        private Track(@Nullable Long listenedAt, TrackMetadata trackMetadata) {
            this.listenedAt = listenedAt;
            this.trackMetadata = trackMetadata;
        }

        private static Track fromRecord(ListenRecord record, boolean withTimestamp) {
            if (!withTimestamp) {
                return new Track(null, TrackMetadata.fromRecord(record));
            } else {
                return new Track(record.timestamp().getEpochSecond(), TrackMetadata.fromRecord(record));
            }
        }
    }

    private static final class TrackMetadata {
        private final String artistName;
        private final String trackName;
        private final @Nullable String releaseName;
        private final AdditionalInfo additionalInfo;

        private TrackMetadata(String artistName, String trackName, @Nullable String releaseName, AdditionalInfo additionalInfo) {
            this.artistName = artistName;
            this.trackName = trackName;
            this.releaseName = releaseName;
            this.additionalInfo = additionalInfo;
        }

        private static TrackMetadata fromRecord(ListenRecord record) {
            String artist = record.track().artist().name();
            String track = record.track().name();
            Optional<Album> album = record.track().album();

            if (!album.isPresent()) {
                return new TrackMetadata(artist, track, null, AdditionalInfo.fromRecord(record));
            } else {
                return new TrackMetadata(artist, track, album.get().name(), AdditionalInfo.fromRecord(record));
            }
        }
    }

    private static final class AdditionalInfo {
        private final long duration;

        private final String mediaPlayer = "Minecraft";
        private final String mediaPlayerVersion = getMinecraftVersion();

        private final String submissionClient = "Kit Tunes";
        private final String submissionClientVersion = Constants.MOD_VERSION;

        private AdditionalInfo(long duration) {
            this.duration = duration;
        }

        private static AdditionalInfo fromRecord(ListenRecord record) {
            return new AdditionalInfo(record.durations().full().getSeconds());
        }

        private static String getMinecraftVersion() {
            return QuiltLoader.getModContainer("minecraft").get().metadata().version().raw();
        }
    }
}
