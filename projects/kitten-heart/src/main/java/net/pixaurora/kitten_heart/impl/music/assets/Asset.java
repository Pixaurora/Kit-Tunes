package net.pixaurora.kitten_heart.impl.music.assets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.pixaurora.catculator.api.error.ClientResponseException;
import net.pixaurora.catculator.api.http.Response;
import net.pixaurora.catculator.impl.util.CryptoUtil;
import net.pixaurora.kitten_heart.impl.Constants;
import net.pixaurora.kitten_heart.impl.KitTunes;

public class Asset {
    private static final String BASE_DOWNLOAD_URL = "https://resources.download.minecraft.net";

    private final String path;
    private final String hash;

    public Asset(String path, String hash) {
        this.path = path;
        this.hash = hash;
    }

    public void sync() throws IOException, ClientResponseException {
        if (Files.exists(this.path()) && this.hash.equals(CryptoUtil.sha1(this.path()))) {
            // Asset is already downloaded and up-to-date, no need to do anything.
            return;
        }

        Response response = KitTunes.CLIENT.get(this.downloadUrl())
                .send();

        if (!response.ok() || !this.hash.equals(CryptoUtil.sha1(response.body()))) {
            throw new RuntimeException("File was not downloaded correctly!");
        } else {
            Files.createDirectories(this.path().getParent());

            Files.write(this.path(), response.body());
        }
    }

    public boolean matches(String name) {
        return this.path().getFileName().toString().startsWith(name);
    }

    public Path path() {
        return Constants.MUSIC_ASSET_PATH.resolve(this.path);
    }

    public String hash() {
        return hash;
    }

    private String downloadUrl() {
        return BASE_DOWNLOAD_URL + "/" + this.hash.substring(0, 2) + "/" + this.hash;
    }
}
