package net.pixaurora.kit_tunes.impl.music;

import java.util.List;
import java.util.Optional;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.resources.ResourceLocation;

public record Album(String key, Optional<ResourceLocation> albumArt) {
	public static final Codec<Album> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Codec.STRING.fieldOf("key").forGetter(Album::key),
			ResourceLocation.CODEC.optionalFieldOf("art_path").forGetter(Album::albumArt)
		).apply(instance, Album::new)
	);
	
	public static List<Track> addAlbum(List<Track> tracks, Album album) {
		return tracks.stream().map(track -> track.addAlbum(album)).toList();
	}

	public static final Codec<List<Track>> CODEC_WITH_TRACKS = Codec.pair(Album.CODEC, Track.CODEC.listOf().fieldOf("tracks").codec())
		.xmap(
			pair -> addAlbum(pair.getSecond(), pair.getFirst()),
			tracks -> Pair.of(tracks.get(0).album().get(), addAlbum(tracks, null))
		);
}
