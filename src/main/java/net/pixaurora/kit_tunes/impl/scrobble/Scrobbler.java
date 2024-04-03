package net.pixaurora.kit_tunes.impl.scrobble;

import java.io.IOException;
import java.util.List;

import com.mojang.serialization.Codec;

import net.pixaurora.kit_tunes.impl.config.dispatch.DispatchGroup;
import net.pixaurora.kit_tunes.impl.config.dispatch.SpecifiesType;
import net.pixaurora.kit_tunes.impl.network.ParsingException;

public interface Scrobbler extends SimpleScrobbler, SpecifiesType<Scrobbler> {
	public static final DispatchGroup<Scrobbler, ScrobblerType<?>> TYPES = new DispatchGroup<>("scrobbler", List.of(LastFMScrobbler.TYPE));

	public static final Codec<Scrobbler> CODEC = TYPES.dispatchCodec();

	public static final int SETUP_PORT = 19686;

	public String username() throws IOException, InterruptedException, ParsingException;
}
