package net.pixaurora.kitten_heart.impl.scrobble.scrobbler;

import java.util.Arrays;

import net.pixaurora.kit_tunes.api.scrobble.ScrobblerId;
import net.pixaurora.kitten_heart.impl.config.dispatch.DispatchGroup;
import net.pixaurora.kitten_heart.impl.config.dispatch.SpecifiesType;
import net.pixaurora.kitten_heart.impl.scrobble.ScrobblerType;
import net.pixaurora.kitten_heart.impl.scrobble.SimpleScrobbler;

public abstract class Scrobbler implements SimpleScrobbler, SpecifiesType<Scrobbler> {
    public static final DispatchGroup<Scrobbler, ScrobblerType<? extends Scrobbler>> TYPES = new DispatchGroup<>(
            "scrobbler", Arrays.asList(LastFMScrobbler.TYPE, LegacyLastFMScrobbler.TYPE, ListenBrainzScrobbler.TYPE));

    public abstract String username();

    public ScrobblerId id() {
        return new ScrobblerId(this.username(), this.type().name());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Scrobbler && this.id().equals(((Scrobbler) other).id());
    }
}
