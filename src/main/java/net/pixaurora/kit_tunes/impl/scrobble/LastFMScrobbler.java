package net.pixaurora.kit_tunes.impl.scrobble;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class LastFMScrobbler implements Scrobbler {
	public static final Codec<LastFMScrobbler> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Codec.STRING.fieldOf("token").forGetter(scrobbler -> scrobbler.token)
		).apply(instance, LastFMScrobbler::new)
	);
	public static final ScrobblerType TYPE = new ScrobblerType("lastfm", CODEC);

	private final String token;

	public LastFMScrobbler(String token) {
		this.token = token;
	}

	public ScrobblerType type() {
		return TYPE;
	}
}
