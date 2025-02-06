package net.pixaurora.kitten_heart.impl.scrobble;

import java.util.Optional;

import net.pixaurora.kitten_heart.impl.config.dispatch.DispatchType;
import net.pixaurora.kitten_heart.impl.scrobble.scrobbler.Scrobbler;
import net.pixaurora.kitten_heart.impl.scrobble.setup.ScrobblerAuthFunction;
import net.pixaurora.kitten_heart.impl.scrobble.setup.ScrobblerSetup;
import net.pixaurora.kitten_heart.impl.scrobble.setup.ServerAuthSetup;

public class ScrobblerType<T extends Scrobbler> implements DispatchType<Scrobbler> {
    private final String name;
    private final Class<T> targetClass;

    private final Optional<ScrobblerSetup> setup;

    public ScrobblerType(String name, Class<T> targetClass, Optional<ScrobblerSetup> setup) {
        super();
        this.name = name;
        this.targetClass = targetClass;
        this.setup = setup;
    }

    public static <T extends Scrobbler> ScrobblerType<T> noSetup(String name, Class<T> targetClass) {
        return new ScrobblerType<>(name, targetClass, Optional.empty());
    }

    public static <T extends Scrobbler> ScrobblerType<T> screenSetup(String name, Class<T> targetClass, ScrobblerSetup setup) {
        return new ScrobblerType<>(name, targetClass, Optional.of(setup));
    }

    public static <T extends Scrobbler> ScrobblerType<T> authServerSetup(String name, Class<T> targetClass,
            String setupUrl, ScrobblerAuthFunction<T> authCallback) {
        return new ScrobblerType<>(name, targetClass, Optional.of(new ServerAuthSetup<>(setupUrl, authCallback)));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Class<T> targetClass() {
        return targetClass;
    }

    public Optional<ScrobblerSetup> setup() {
        return this.setup;
    }
}
