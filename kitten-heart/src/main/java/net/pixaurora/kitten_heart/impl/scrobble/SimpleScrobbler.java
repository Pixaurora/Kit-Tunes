package net.pixaurora.kitten_heart.impl.scrobble;

import net.pixaurora.kitten_heart.impl.error.KitTunesException;

public interface SimpleScrobbler {
    public void startScrobbling(ScrobbleInfo track) throws KitTunesException;

    public void completeScrobbling(ScrobbleInfo track) throws KitTunesException;
}
