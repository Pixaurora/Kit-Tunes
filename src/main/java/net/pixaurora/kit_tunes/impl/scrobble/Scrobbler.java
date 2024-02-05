package net.pixaurora.kit_tunes.impl.scrobble;

import java.util.List;

import com.mojang.serialization.Codec;

import net.pixaurora.kit_tunes.impl.config.SerialType;
import net.pixaurora.kit_tunes.impl.config.SpecifiesType;

public interface Scrobbler extends SpecifiesType<Scrobbler> {
	public static final List<ScrobblerType> TYPES = List.of(LastFMScrobbler.TYPE);
	public static final SerialType.Group<Scrobbler, ScrobblerType> TYPE_GROUP = new SerialType.Group<>("scrobbler", TYPES);

	public static final Codec<Scrobbler> CODEC = TYPE_GROUP.dispatchCodec();
}
