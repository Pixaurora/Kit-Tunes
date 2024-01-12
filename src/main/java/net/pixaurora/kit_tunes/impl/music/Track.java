package net.pixaurora.kit_tunes.impl.music;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public record Track(ResourceLocation path, Optional<String> artistKey, Optional<Album> album) {
	public static final Codec<Track> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("path").forGetter(Track::path),
			Codec.STRING.optionalFieldOf("artist_key").forGetter(Track::artistKey),
			Album.CODEC.optionalFieldOf("album").forGetter(Track::album)
		).apply(instance, Track::new)
	);

	public Track addAlbum(Album album) {
		return new Track(this.path, this.artistKey, Optional.of(album));
	}

	public MutableComponent title() {
		String path = this.path.toString();
		return Component.translatableWithFallback(
			"kit_tunes.track." + path.replaceAll(":|\\.", "."),
			MusicPathConverter.titleCase(path.substring(path.lastIndexOf("/") + 1))
		);
	}

	public MutableComponent artist() {
		return Component.translatable("kit_tunes.artist." + artistKey.orElse("unknown"));
	}
}
