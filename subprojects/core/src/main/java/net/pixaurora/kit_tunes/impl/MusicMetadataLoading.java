package net.pixaurora.kit_tunes.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;

import net.pixaurora.kit_tunes.impl.config.Serialization;
import net.pixaurora.kit_tunes.impl.music.Album;
import net.pixaurora.kit_tunes.impl.music.AlbumImpl;
import net.pixaurora.kit_tunes.impl.music.Artist;
import net.pixaurora.kit_tunes.impl.music.ArtistImpl;
import net.pixaurora.kit_tunes.impl.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.resource.TransformsInto;

public class MusicMetadataLoading {
	private static Path[] filterJSONFilesIn(String subdirectory, Path root) {
		try {
			return Files.walk(root)
				.filter(Files::isRegularFile)
				.filter(path -> {
					String fileName = path.getFileName().toString();
					String parentName = path.getParent().getFileName().toString();

					return fileName.endsWith(".json") && parentName.equals(subdirectory);
				})
				.toArray(size -> new Path[size]);
		} catch (IOException e) {
			throw new RuntimeException("Failed to filter paths!");
		}
	}

	private static <T, Data extends TransformsInto<T>> List<T> load(String subdirectory, Class<Data> typeToken, Path root, Function<Path, ResourcePath> pathTransformer) {
		List<T> items = new ArrayList<>();

		for (Path itemFile : filterJSONFilesIn(subdirectory, root)) {
			Data dataItem;
			try {
				BufferedReader reader = Files.newBufferedReader(itemFile);

				dataItem = Serialization.serializer().fromJson(reader, typeToken);
			} catch (IOException e) {
				throw new RuntimeException("Failed to read `" + itemFile + "`!");
			}

			items.add(dataItem.transform(pathTransformer.apply(itemFile)));
		}

		return items;
	}

	public static void loadAll(Path root, Function<Path, ResourcePath> pathTransformer) {
		for (Artist artist : load("artists", ArtistImpl.Data.class, root, pathTransformer)) {
			MusicMetadata.addArtist(artist);
		}

		for (Album album : load("albums", AlbumImpl.Data.class, root, pathTransformer)) {
			MusicMetadata.addAlbum(album);
		}
	}

	public static void loadMetadata() {
		for (ModContainer mod : QuiltLoader.getAllMods()) {
			loadAll(mod.getPath("."), path -> {
				// Removes /./ and so on
				path = path.normalize();

				// Creates a string like MOD_ID/albums/example_album.json
				String resourcePath = mod.metadata().id() + path;

				return ResourcePath.fromString(resourcePath.replace(".json", ""), "/");
			});
		}
	}
}
