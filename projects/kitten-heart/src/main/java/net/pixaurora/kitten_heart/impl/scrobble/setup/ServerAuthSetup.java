package net.pixaurora.kitten_heart.impl.scrobble.setup;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import net.pixaurora.catculator.api.http.Server;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.error.UnhandledKitTunesException;
import net.pixaurora.kitten_heart.impl.scrobble.scrobbler.Scrobbler;
import net.pixaurora.kitten_heart.impl.ui.screen.scrobbler.setup.ScrobblerOauthSetupScreen;

public class ServerAuthSetup<T extends Scrobbler> implements ScrobblerSetup {
    private final String setupUrl;
    private final ScrobblerAuthFunction<T> authFunction;

    public ServerAuthSetup(String setupUrl, ScrobblerAuthFunction<T> authFunction) {
        this.setupUrl = setupUrl;
        this.authFunction = authFunction;
    }

    public String url() {
        return this.setupUrl;
    }

    @Override
    public Screen setupScreen(Screen previous) {
        return new ScrobblerOauthSetupScreen<>(previous, this);
    }

    public SetupProcess<T> run() throws IOException {
        Server server = Server.create();

        return new SetupProcess<>(server, authFunction);
    }

    public static class SetupProcess<T extends Scrobbler> implements Closeable {
        private final Server server;
        private final CompletableFuture<T> awaitedScrobbler;

        public SetupProcess(Server server, ScrobblerAuthFunction<T> authFunction) {
            this.server = server;
            this.awaitedScrobbler = CompletableFuture.supplyAsync(this::run)
                    .whenComplete((token, error) -> server.close())
                    .thenApply(token -> {
                        try {
                            return authFunction.createScrobbler(KitTunes.CLIENT, token);
                        } catch (IOException e) {
                            throw new UnhandledKitTunesException(e);
                        }
                    });
        }

        private String run() {
            try {
                return this.server.run();
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
}
