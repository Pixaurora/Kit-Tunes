package net.pixaurora.kit_tunes.impl.music;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;

import com.mojang.serialization.JsonOps;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class MusicPathConverter {
	private static @Nullable Map<ResourceLocation, AlbumTrack> cachedTracks;

	private static Map<ResourceLocation, AlbumTrack> loadTracks() throws IOException {
		Map<ResourceLocation, AlbumTrack> tracks = new HashMap<>();

		for (ModContainer mod : QuiltLoader.getAllMods()) {
			Path metadataFile = mod.getPath("music_metadata.json");

			if (Files.exists(metadataFile)) {
				String contents = Files.readString(metadataFile, StandardCharsets.UTF_8);

				List<Album> loadedAlbums = Album.CODEC.listOf()
					.decode(JsonOps.INSTANCE, GsonHelper.parseArray(contents))
					.getOrThrow(false, RuntimeException::new)
					.getFirst();
				
				for (Album album : loadedAlbums) {
					for (AlbumTrack track : album.tracks()) {
						tracks.put(track.path(), track);
					}
				}
			}
		}

		return tracks;
	}

	public static synchronized AlbumTrack getTrack(ResourceLocation soundPath) {
		if (cachedTracks == null) {
			try {
				cachedTracks = loadTracks();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return cachedTracks.computeIfAbsent(soundPath, path -> new AlbumTrack(path));
	}

	public static String titleCase(String snakeCase) {
		return String.join(" ", Stream.of(snakeCase.split("_")).map(StringUtils::capitalize).toList());
	}
}
