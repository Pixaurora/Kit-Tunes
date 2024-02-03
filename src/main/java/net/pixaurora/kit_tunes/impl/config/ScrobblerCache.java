package net.pixaurora.kit_tunes.impl.config;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;

import net.pixaurora.kit_tunes.impl.scrobble.Scrobbler;
import net.pixaurora.kit_tunes.impl.scrobble.ScrobblerType;

public class ScrobblerCache implements Scrobbler {
	public static final Codec<ScrobblerCache> CODEC = Scrobbler.CODEC.listOf().xmap(ScrobblerCache::new, ScrobblerCache::scrobblers);

	private List<Scrobbler> scrobblers;

	public ScrobblerCache(List<Scrobbler> scrobblers) {
		this.scrobblers = new ArrayList<>(scrobblers);
	}

	@Override
	public ScrobblerType type() {
		return null;
	}

	public List<Scrobbler> scrobblers() {
		return this.scrobblers;
	}

	public void addScrobbler(Scrobbler scrobbler) {
		this.scrobblers.add(scrobbler);
	}
}
