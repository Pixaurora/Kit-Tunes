package net.pixaurora.kitten_heart.impl.scrobble;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import net.pixaurora.catculator.api.http.Server;

public class ScrobblerSetup<T extends Scrobbler> implements Closeable {
    private final Server server;
    private final CompletableFuture<T> awaitedScrobbler;

    public ScrobblerSetup(Server server, ScrobblerType<T> scrobblerType, long timeout, TimeUnit unit) {
        this.server = server;
        this.awaitedScrobbler = CompletableFuture.supplyAsync(this::run).whenComplete((token, error) -> server.close())
                .thenApply(token -> {
                    return scrobblerType.setupMethod().createScrobbler(token);
                });
    }

    private String run() {
        try {
            return this.server.runServer();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't finish running server!", e);
        }
    }

    public boolean isComplete() {
        return this.awaitedScrobbler.isDone();
    }

    public T get() throws ExecutionException, InterruptedException {
        return this.awaitedScrobbler.get();
    }

    public void cancel() {
        this.close();
        this.awaitedScrobbler.cancel(false);
    }

    @Override
    public void close() {
        this.server.close();
    }
}
