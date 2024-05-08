package net.pixaurora.kit_tunes.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public class MusicMetadata {
	private static List<Artist> ARTISTS = new ArrayList<>();
	private static List<Album> ALBUMS = new ArrayList<>();

	private static Map<String, Track> MATCH_TO_TRACK = new HashMap<>();

	private static boolean HAS_LOADED = false;

	public static void lazyLoad() {
		if (HAS_LOADED) {
			return;
		} else {
			HAS_LOADED = true;
			MusicMetadataLoading.loadMetadata();
		}
	}

	public static void addArtist(Artist artist) {
		ARTISTS.add(artist);
	}

	public static void addAlbum(Album album) {
		ALBUMS.add(album);

		for (Track track : album.tracks()) {
			for (String match : track.matches()) {
				MATCH_TO_TRACK.put(match, track);
			}
		}
	}

	public static Artist findArtist(ResourcePath path) {
		lazyLoad();

		for (Artist artist : ARTISTS) {
			if (path.equals(artist.path())) {
				return artist;
			}
		}

		throw new RuntimeException("Could not find artist at `" + path + "`!");
	}

	public static Track matchTrack(String trackPath) {
		lazyLoad();

		String[] splitPath = trackPath.split("/");
		String filename = splitPath[splitPath.length - 1];

		@Nullable Track track = MATCH_TO_TRACK.get(filename);

		if (track != null) {
			return track;
		} else {
			throw new RuntimeException("Could not match a track for `" + trackPath + "`!");
		}
	}
}
