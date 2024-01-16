package net.pixaurora.kit_tunes.impl.music;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.resources.ResourceLocation;

public record Track(ResourceLocation path, Optional<String> artistKey) {
	public static final Codec<Track> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("path").forGetter(Track::path),
			Codec.STRING.optionalFieldOf("artist").forGetter(Track::artistKey)
		).apply(instance, Track::new)
	);
}
