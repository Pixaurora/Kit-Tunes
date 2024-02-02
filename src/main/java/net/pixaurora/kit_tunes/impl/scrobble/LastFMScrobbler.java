package net.pixaurora.kit_tunes.impl.scrobble;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.pixaurora.kit_tunes.impl.config.SerialType;

public class LastFMScrobbler implements Scrobbler {
	public static final Codec<LastFMScrobbler> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Codec.STRING.fieldOf("token").forGetter(scrobbler -> scrobbler.token)
		).apply(instance, LastFMScrobbler::new)
	);
	public static final SerialType<Scrobbler> TYPE = new SerialType<>("lastfm", CODEC);

	private final String token;

	public LastFMScrobbler(String token) {
		this.token = token;
	}

	@Override
	public SerialType<? extends Scrobbler> type() {
		return TYPE;
	}
}
