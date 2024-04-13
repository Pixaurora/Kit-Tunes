package net.pixaurora.kit_tunes.impl;

import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kit_tunes.impl.music.Album;
import net.pixaurora.kit_tunes.impl.music.Artist;
import net.pixaurora.kit_tunes.impl.music.Track;
import net.pixaurora.kit_tunes.impl.resource.ResourcePath;

public class MusicMetadata {
	private static List<Artist> ARTISTS = new ArrayList<>();

	private static List<Track> TRACKS = new ArrayList<>();

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
		for (Track track : album.tracks()) {
			TRACKS.add(track);
		}
	}

	public static Artist findArtist(ResourcePath path) {
		lazyLoad();

		for (Artist artist : ARTISTS) {
			// This also is probably bad and needs to be improved
			if (path.toString().equals(artist.path().toString())) {
				return artist;
			}
		}

		throw new RuntimeException("Could not find artist at `" + path + "`!");
	}

	public static Track matchTrack(String trackPath) {
		lazyLoad();

		for (Track track : TRACKS) {
			for (String match : track.matches()) {
				if (trackPath.contains(match)) {
					return track;
				}
			}
		}

		throw new RuntimeException("Could not match a track for `" + trackPath + "`!");
	}
}
