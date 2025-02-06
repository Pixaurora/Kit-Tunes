package net.pixaurora.kitten_heart.impl.scrobble.scrobbler;

import java.util.Arrays;

import net.pixaurora.kit_tunes.api.scrobble.ScrobblerId;
import net.pixaurora.kitten_heart.impl.config.dispatch.DispatchGroup;
import net.pixaurora.kitten_heart.impl.config.dispatch.SpecifiesType;
import net.pixaurora.kitten_heart.impl.scrobble.ScrobblerType;
import net.pixaurora.kitten_heart.impl.scrobble.SimpleScrobbler;

public interface Scrobbler extends SimpleScrobbler, SpecifiesType<Scrobbler> {
    public static final DispatchGroup<Scrobbler, ScrobblerType<? extends Scrobbler>> TYPES = new DispatchGroup<>(
            "scrobbler", Arrays.asList(LastFMScrobbler.TYPE, LegacyLastFMScrobbler.TYPE, ListenBrainzScrobbler.TYPE));

    public String username();

    public default ScrobblerId id() {
        return new ScrobblerId(this.username(), this.type().name());
    }
}
