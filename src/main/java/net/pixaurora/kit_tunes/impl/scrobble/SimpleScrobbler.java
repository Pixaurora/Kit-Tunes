package net.pixaurora.kit_tunes.impl.scrobble;

import java.io.IOException;

import net.pixaurora.kit_tunes.impl.network.ParsingException;

public interface SimpleScrobbler {
	public void startScrobbling(ScrobbleInfo track) throws IOException, InterruptedException, ParsingException;

	public void completeScrobbling(ScrobbleInfo track) throws IOException, InterruptedException, ParsingException;
}
