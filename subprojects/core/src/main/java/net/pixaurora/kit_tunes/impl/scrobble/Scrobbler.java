package net.pixaurora.kit_tunes.impl.scrobble;

import java.util.List;

import net.pixaurora.kit_tunes.impl.config.dispatch.DispatchGroup;
import net.pixaurora.kit_tunes.impl.config.dispatch.SpecifiesType;
import net.pixaurora.kit_tunes.impl.error.KitTunesBaseException;

public interface Scrobbler extends SimpleScrobbler, SpecifiesType<Scrobbler> {
    public static final DispatchGroup<Scrobbler, ScrobblerType<? extends Scrobbler>> TYPES = new DispatchGroup<>(
            "scrobbler", List.of(LastFMScrobbler.TYPE));

    public static final int SETUP_PORT = 19686;

    public String username() throws KitTunesBaseException;
}
