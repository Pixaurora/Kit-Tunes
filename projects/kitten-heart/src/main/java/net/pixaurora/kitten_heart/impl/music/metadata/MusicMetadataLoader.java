package net.pixaurora.kitten_heart.impl.music.metadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.config.Serialization;
import net.pixaurora.kitten_heart.impl.music.AlbumImpl;
import net.pixaurora.kitten_heart.impl.music.ArtistImpl;
import net.pixaurora.kitten_heart.impl.music.TrackImpl;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathUtils;
import net.pixaurora.kitten_heart.impl.resource.TransformsTo;
import net.pixaurora.kitten_heart.impl.util.Pair;

public class MusicMetadataLoader {
    private static <T, Data extends TransformsTo<T>> List<T> load(List<Path> jsonFiles, Class<Data> typeToken) {
        List<T> items = new ArrayList<>();

        for (Path itemFile : jsonFiles) {
            Data itemData;
            try {
                BufferedReader reader = Files.newBufferedReader(itemFile);

                itemData = Serialization.serializer().fromJson(reader, typeToken);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read `" + itemFile + "`!");
            }

            Optional<ResourcePath> path = ResourcePathUtils.metadataPathToResource(itemFile);

            if (!path.isPresent()) {
                throw new RuntimeException("Path can't be formed for `" + itemFile + "`!");
            }

            items.add(itemData.transform(path.get()));
        }

        return items;
    }

    private static List<Path> jsonFiles(Path assetsRoot, String subdirName) throws IOException {
        List<Path> jsonFiles = new ArrayList<>();

        if (!Files.isDirectory(assetsRoot)) {
            return jsonFiles;
        }

        Iterator<Path> namespaceDirs = Files.list(assetsRoot).iterator();
        while (namespaceDirs.hasNext()) {
            Path namespace = namespaceDirs.next();
            Path subdir = namespace.resolve("music_metadata").resolve(subdirName);

            if (!Files.isDirectory(subdir)) {
                continue;
            }

            Iterator<Path> potentialJsonFiles = Files.walk(subdir).iterator();
            while (potentialJsonFiles.hasNext()) {
                Path potentialJson = potentialJsonFiles.next();

                if (Files.isRegularFile(potentialJson) && potentialJson.toString().endsWith(".json")) {
                    jsonFiles.add(potentialJson);
                }
            }
        }

        return jsonFiles;
    }

    private static List<Path> jsonFilesInMods(String subdirName) {
        List<Path> files = new ArrayList<>();

        for (ModContainer mod : QuiltLoader.getAllMods()) {
            Path assetsRoot = mod.getPath("assets");

            try {
                files.addAll(jsonFiles(assetsRoot, subdirName));
            } catch (IOException e) {
                throw new RuntimeException("Exception thrown while searching for music metadata json files!", e);
            }
        }

        return files;
    }

    public static List<Path> albumFiles() {
        return jsonFilesInMods("album");
    }

    public static List<Path> artistFiles() {
        return jsonFilesInMods("artist");
    }

    public static List<Path> trackFiles() {
        return jsonFilesInMods("track");
    }

    public static void load(MusicMetadataImpl impl, List<Path> albumFiles, List<Path> artistFiles,
            List<Path> trackFiles) {
        for (Artist artist : load(artistFiles, ArtistImpl.FromData.class)) {
            impl.add(artist);
        }

        for (Track track : load(trackFiles, TrackImpl.FromData.class)) {
            impl.add(track);
        }

        for (Pair<Album, List<Track>> albumAndIncludedTracks : load(albumFiles, AlbumImpl.FromData.class)) {
            Album album = albumAndIncludedTracks.first();
            List<Track> includedTracks = albumAndIncludedTracks.second();

            impl.add(album);

            for (Track includedTrack : includedTracks) {
                impl.add(includedTrack);
            }
        }
    }

}
