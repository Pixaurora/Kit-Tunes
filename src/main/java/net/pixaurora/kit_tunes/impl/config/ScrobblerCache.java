package net.pixaurora.kit_tunes.impl.config;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;

import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.error.KitTunesBaseException;
import net.pixaurora.kit_tunes.impl.scrobble.ScrobbleInfo;
import net.pixaurora.kit_tunes.impl.scrobble.Scrobbler;
import net.pixaurora.kit_tunes.impl.scrobble.SimpleScrobbler;

public class ScrobblerCache implements SimpleScrobbler {
	public static final Codec<ScrobblerCache> CODEC = Scrobbler.CODEC
		.listOf()
		.xmap(ScrobblerCache::new, ScrobblerCache::scrobblers);

	private List<Scrobbler> scrobblers;

	public ScrobblerCache(List<Scrobbler> scrobblers) {
		this.scrobblers = new ArrayList<>(scrobblers);
	}

	public List<Scrobbler> scrobblers() {
		return this.scrobblers;
	}

	public void addScrobbler(Scrobbler scrobbler) {
		this.scrobblers.add(scrobbler);
	}

	@Override
	public void startScrobbling(ScrobbleInfo track) {
		this.handleScrobbling(scrobbler -> scrobbler.startScrobbling(track));
	}

	@Override
	public void completeScrobbling(ScrobbleInfo track) {
		this.handleScrobbling(scrobbler -> scrobbler.completeScrobbling(track));
	}

	private void handleScrobbling(ScrobbleAction action) {
		for (Scrobbler scrobbler : this.scrobblers) {
			try {
				action.doFor(scrobbler);
			} catch (Exception e) {
				KitTunes.LOGGER.error("Ignoring exception encountered while scrobbling.", e);
			}
		}
	}

	private static interface ScrobbleAction {
		public void doFor(Scrobbler scrobbler) throws KitTunesBaseException;
	}
}
