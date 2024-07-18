package net.pixaurora.kit_tunes.impl.scrobble;

import net.pixaurora.kit_tunes.impl.error.KitTunesException;

public interface SimpleScrobbler {
    public void startScrobbling(ScrobbleInfo track) throws KitTunesException;

    public void completeScrobbling(ScrobbleInfo track) throws KitTunesException;
}
