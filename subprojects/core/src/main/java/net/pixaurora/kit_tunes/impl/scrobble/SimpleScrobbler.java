package net.pixaurora.kit_tunes.impl.scrobble;

import net.pixaurora.kit_tunes.impl.error.KitTunesBaseException;

public interface SimpleScrobbler {
    public void startScrobbling(ScrobbleInfo track) throws KitTunesBaseException;

    public void completeScrobbling(ScrobbleInfo track) throws KitTunesBaseException;
}
