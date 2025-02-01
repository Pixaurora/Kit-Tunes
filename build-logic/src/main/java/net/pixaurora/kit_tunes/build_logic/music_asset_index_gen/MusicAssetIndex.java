package net.pixaurora.kit_tunes.build_logic.music_asset_index_gen;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public record MusicAssetIndex(Map<MusicCategory, List<Asset>> index) {
    public static String VERSION_MANIFEST_LOCATION = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";

    public static MusicAssetIndex getFor(String versionId, Gson serializer)
            throws IOException, InterruptedException {
        var versionUrl = getVersionUrl(versionId, serializer);
        var assetIndexUrl = getAssetIndexUrl(versionUrl, serializer);

        var assetIndex = getAssetIndex(assetIndexUrl, serializer);

        return buildMusicAssetIndex(assetIndex);
    }

    public static MusicAssetIndex buildMusicAssetIndex(AssetIndex assetIndex) {
        Map<MusicCategory, List<Asset>> musicAssets = new HashMap<>();

        for (Map.Entry<String, AssetIndex.AssetData> assetEntry : assetIndex.objects.entrySet()) {
            var path = assetEntry.getKey();

            for (MusicCategory category : MusicCategory.values()) {
                if (category.matches(path)) {
                    var matchingAssets = musicAssets.computeIfAbsent(category, category0 -> new ArrayList<>());
                    var assetData = assetEntry.getValue();

                    matchingAssets.add(new Asset(path, assetData.hash));

                    break;
                }
            }
        }

        return new MusicAssetIndex(musicAssets);
    }

    private static String getVersionUrl(String versionId, Gson serializer) throws IOException, InterruptedException {
        var body = httpGetBody(VERSION_MANIFEST_LOCATION);

        var versionManifest = serializer.fromJson(body, VersionManifest.class);

        for (var version : versionManifest.versions) {
            if (version.id.equals(versionId)) {
                return version.url;
            }
        }

        throw new RuntimeException("Version `" + versionId + "` not present in version manifest!");
    }

    private static String getAssetIndexUrl(String url, Gson serializer)
            throws IOException, InterruptedException {
        var body = httpGetBody(url);

        var versionData = serializer.fromJson(body, VersionData.class);

        return versionData.assetIndex.url;
    }

    private static AssetIndex getAssetIndex(String url, Gson serializer) throws IOException, InterruptedException {
        var body = httpGetBody(url);

        return serializer.fromJson(body, AssetIndex.class);
    }

    private static String httpGetBody(String uri) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();

        var response = client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .GET()
                        .build(),
                BodyHandlers.ofString());

        return response.body();
    }

    public static record Asset(String path, String hash) {
    }

    private static record VersionManifest(List<VersionMetadata> versions) {
        public static record VersionMetadata(String id, String url) {
        }
    }

    private static record VersionData(AssetIndexMetadata assetIndex) {
        public static record AssetIndexMetadata(String url) {
        }
    }

    private static record AssetIndex(Map<String, AssetData> objects) {
        private static record AssetData(String hash, int size) {
        }
    }
}
