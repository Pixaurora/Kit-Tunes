package net.pixaurora.kitten_heart.impl.scrobble;

import java.util.Arrays;

import net.pixaurora.kitten_heart.impl.config.dispatch.DispatchGroup;
import net.pixaurora.kitten_heart.impl.config.dispatch.SpecifiesType;
import net.pixaurora.kitten_heart.impl.error.KitTunesException;

public interface Scrobbler extends SimpleScrobbler, SpecifiesType<Scrobbler> {
    public static final DispatchGroup<Scrobbler, ScrobblerType<? extends Scrobbler>> TYPES = new DispatchGroup<>(
            "scrobbler", Arrays.asList(LastFMScrobbler.TYPE));

    public String username() throws KitTunesException;
}
