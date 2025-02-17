package net.pixaurora.kitten_heart.impl.music.assets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import net.pixaurora.catculator.api.error.ClientResponseException;

public class MusicAssetIndex {
    private static final Random random = new Random();

    private final Map<MusicCategory, List<Asset>> index;

    public MusicAssetIndex(Map<MusicCategory, List<Asset>> index) {
        this.index = index;
    }

    public void sync() {
        for (List<Asset> assets : index.values()) {
            for (Asset asset : assets) {
                try {
                    asset.sync();
                } catch (IOException | ClientResponseException e) {
                    throw new RuntimeException(
                            "Failed to download asset `" + asset.path() + "`! Hash: `" + asset.hash() + "`", e);
                }
            }
        }
    }

    public List<Asset> getAssets(MusicCategory category) {
        return this.index.get(category);
    }

    public Asset random(MusicCategory category) {
        List<Asset> outcomes = this.getAssets(category);

        return outcomes.get(random.nextInt(outcomes.size()));
    }

    public Optional<Asset> match(String name) {
        for (List<Asset> assets : index.values()) {
            for (Asset asset : assets) {
                if (asset.matches(name)) {
                    return Optional.of(asset);
                }
            }
        }

        return Optional.empty();
    }
}
