package net.pixaurora.kitten_heart.impl.scrobble;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import net.pixaurora.kitten_heart.impl.network.SetupServer;

public class ScrobblerSetup<T extends Scrobbler> {
    private final SetupServer server;
    private final CompletableFuture<T> awaitedScrobbler;

    public ScrobblerSetup(SetupServer server, ScrobblerType<T> scrobblerType, long timeout, TimeUnit unit) {
        this.server = server;
        this.awaitedScrobbler = server.awaitedToken().whenComplete((token, error) -> server.cleanup())
                .thenApply(token -> {
                    return scrobblerType.setupMethod().createScrobbler(token);
                });
    }

    public boolean isComplete() {
        return this.awaitedScrobbler.isDone();
    }

    public T get() throws ExecutionException, InterruptedException {
        return this.awaitedScrobbler.get();
    }

    public void cancel() {
        this.awaitedScrobbler.cancel(false);
        this.server.cleanup();
    }
}
