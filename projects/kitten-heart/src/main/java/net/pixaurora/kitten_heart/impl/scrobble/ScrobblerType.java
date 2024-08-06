package net.pixaurora.kitten_heart.impl.scrobble;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.pixaurora.kitten_heart.impl.config.dispatch.DispatchType;
import net.pixaurora.kitten_heart.impl.error.KitTunesException;
import net.pixaurora.kitten_heart.impl.network.SetupServer;

public class ScrobblerType<T extends Scrobbler> implements DispatchType<Scrobbler> {
    private final String name;
    private final Class<T> targetClass;
    private final String setupURL;
    private final String tokenPrefix;
    private final SetupMethod<T> setupMethod;

    public ScrobblerType(String name, Class<T> targetClass, String setupURL, String tokenPrefix,
            SetupMethod<T> setupMethod) {
        super();
        this.name = name;
        this.targetClass = targetClass;
        this.setupURL = setupURL;
        this.tokenPrefix = tokenPrefix;
        this.setupMethod = setupMethod;
    }

    public ScrobblerSetup<T> setup(long timeout, TimeUnit unit) throws IOException {
        SetupServer server = SetupServer.create(this.tokenPrefix);

        return new ScrobblerSetup<>(server, this, timeout, unit);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Class<T> targetClass() {
        return targetClass;
    }

    public String setupURL() {
        return setupURL;
    }

    public String tokenPrefix() {
        return tokenPrefix;
    }

    public SetupMethod<T> setupMethod() {
        return setupMethod;
    }

    public static interface SetupMethod<T> {
        public T createScrobbler(String token) throws KitTunesException;
    }

}
