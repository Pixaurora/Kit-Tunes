package net.pixaurora.kitten_heart.impl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import net.pixaurora.catculator.api.http.Client;
import net.pixaurora.kitten_heart.impl.scrobble.scrobbler.Scrobbler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.concurrent.KitTunesThreadFactory;
import net.pixaurora.kitten_heart.impl.config.ConfigManager;
import net.pixaurora.kitten_heart.impl.config.ScrobblerCache;
import net.pixaurora.kitten_heart.impl.music.history.ListenHistory;
import net.pixaurora.kitten_heart.impl.music.metadata.MusicMetadata;
import net.pixaurora.kitten_heart.impl.music.metadata.MusicMetadataLoader;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathImpl;
import net.pixaurora.kitten_heart.impl.service.UICompat;
import net.pixaurora.kitten_heart.impl.service.MusicCompat;
import net.pixaurora.kitten_heart.impl.service.ServiceLoading;
import net.pixaurora.catculator.impl.Catculator;

public class KitTunes {
    static {
        Catculator.init();

        try {
            CLIENT = Client.create(buildUserAgent());
        } catch (IOException e) {
            throw new RuntimeException("Failed to create HTTP client", e);
        }
    }

    public static final Client CLIENT;

    public static final Logger LOGGER = LoggerFactory.getLogger(Constants.MOD_ID);

    public static ConfigManager<ScrobblerCache> SCROBBLER_CACHE;
    public static ConfigManager<ListenHistory> LISTEN_HISTORY;

    public static final UICompat UI_LAYER = ServiceLoading.loadJustOneOrThrow(UICompat.class);

    public static final MusicCompat MUSIC_LAYER = ServiceLoading.loadJustOneOrThrow(MusicCompat.class);

    public static final List<MusicEventListener> MUSIC_LISTENERS = ServiceLoading.loadAll(MusicEventListener.class);

    public static final Executor EXECUTOR = Executors.newFixedThreadPool(2, new KitTunesThreadFactory());

    public static ResourcePath resource(String path) {
        return ResourcePathImpl.fromString(Constants.MOD_ID + ":" + path);
    }

    public static void init() {
        // Mostly just init the class to make sure all static fields are set, etc.
        // It's not a problem in modern versions, but in older Java versions not
        // doing this can sometimes cause issues.
        MusicMetadata.init(MusicMetadataLoader.albumFiles(), MusicMetadataLoader.artistFiles(),
                MusicMetadataLoader.trackFiles());

        SCROBBLER_CACHE = new ConfigManager<>(
                Constants.SCROBBLER_CACHE_PATH, ScrobblerCache.class, ScrobblerCache::defaults);
        LISTEN_HISTORY = new ConfigManager<>(Constants.LISTEN_HISTORY_PATH,
                ListenHistory.class, ListenHistory::defaults);
    }

    public static void tick() {
        EventHandling.tick();
    }

    public static void stop() {
        EventHandling.stop();
        CLIENT.close();
    }

    public static void addScrobbler(Scrobbler scrobbler) throws IOException {
        KitTunes.SCROBBLER_CACHE.execute(scrobblers -> scrobblers.addScrobbler(scrobbler));
        KitTunes.SCROBBLER_CACHE.save();

    }
    private static String buildUserAgent() {
        return "Kit Tunes/" + Constants.MOD_VERSION + " (+" + Constants.HOMEPAGE + ")";
    }
}
