package net.pixaurora.kitten_heart.impl.network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import net.pixaurora.kitten_heart.impl.scrobble.Scrobbler;

public class SetupServer {
    private final HttpServer server;

    private final String tokenPrefix;
    private final CompletableFuture<String> awaitedToken;

    private SetupServer(HttpServer server, String tokenPrefix) {
        this.server = server;
        this.tokenPrefix = tokenPrefix;
        this.awaitedToken = new CompletableFuture<>();

        server.createContext("/", this::defaultPath);
    }

    public static SetupServer create(String tokenPrefix) throws IOException {
        return new SetupServer(HttpServer.create(new InetSocketAddress("localhost", Scrobbler.SETUP_PORT), 0),
                tokenPrefix);
    }

    public CompletableFuture<String> awaitedToken() {
        this.server.start();

        return this.awaitedToken;
    }

    public void cleanup() {
        this.server.stop(0);
    }

    private final void defaultPath(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();

        if (!query.startsWith(this.tokenPrefix)) {
            this.sendResponse(exchange, 400, "Token not found.");
        } else {
            this.sendResponse(exchange, 200, "Scrobbler token received! You have to close this tab now.");

            String token = query.substring(this.tokenPrefix.length());
            awaitedToken.complete(token);
        }
    }

    private final void sendResponse(HttpExchange exchange, int code, String text) throws IOException {
        exchange.sendResponseHeaders(code, text.length());

        OutputStream response = exchange.getResponseBody();

        response.write(text.getBytes());
        response.flush();
        response.close();
    }
}
