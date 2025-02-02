package net.pixaurora.kitten_heart.impl.music.assets;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.google.gson.stream.JsonReader;

import net.pixaurora.kitten_heart.impl.Constants;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.config.Serialization;

public class MusicAssetManager {
    private final MusicAssetIndex index;
    private final CompletableFuture<Void> syncTask;

    private MusicAssetManager(MusicAssetIndex index) {
        this.index = index;
        this.syncTask = CompletableFuture.runAsync(this.index::sync, KitTunes.EXECUTOR);
    }

    public static MusicAssetManager load() throws IOException {
        Reader reader = Files.newBufferedReader(Constants.MUSIC_ASSET_INDEX_PATH, StandardCharsets.UTF_8);

        MusicAssetIndex index = Serialization.serializer().fromJson(new JsonReader(reader), MusicAssetIndex.class);

        return new MusicAssetManager(index);
    }

    public boolean isReady() {
        return this.syncTask.isDone();
    }

    public Optional<MusicAssetIndex> index() {
        if (this.isReady()) {
            return Optional.of(this.index);
        } else {
            return Optional.empty();
        }
    }
}
