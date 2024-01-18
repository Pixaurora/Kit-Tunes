package net.pixaurora.kit_tunes.impl.music;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.resources.ResourceLocation;

public record Album(String key, Optional<ResourceLocation> albumArt, List<Track> trackInfo) {
	public static final Codec<Album> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Codec.STRING.fieldOf("album").forGetter(Album::key),
			ResourceLocation.CODEC.optionalFieldOf("art_path").forGetter(Album::albumArt),
			Track.CODEC.listOf().fieldOf("tracks").forGetter(Album::trackInfo)
		).apply(instance, Album::new)
	);

	public List<AlbumTrack> tracks() {
		return this.trackInfo.stream()
			.map(trackInfo -> new AlbumTrack(trackInfo, Optional.of(this)))
			.toList();
	}
}
