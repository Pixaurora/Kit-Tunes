package net.pixaurora.catculator.impl.http;

import net.pixaurora.catculator.api.http.Client;
import net.pixaurora.catculator.api.http.RequestBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ClientImpl implements Client {
    private final long ptr;
    private boolean active = true;

    public ClientImpl(String userAgent) throws IOException {
        this.ptr = create(userAgent);
    }

    @Override
    public @NotNull RequestBuilder get(String url) {
        if (this.active) {
            return this.request0("GET", url);
        } else {
            throw new RuntimeException("HTTP client inactive.");
        }
    }

    @Override
    public @NotNull RequestBuilder post(String url) {
        if (this.active) {
            return this.request0("POST", url);
        } else {
            throw new RuntimeException("HTTP client inactive.");
        }
    }

    @Override
    public @NotNull RequestBuilder request(String method, String url) {
        if (this.active) {
            return this.request0(method, url);
        } else {
            throw new RuntimeException("HTTP client inactive.");
        }
    }

    @Override
    public void close() {
        if (!this.active) {
            return;
        }

        this.drop();
        this.active = false;
    }

    private native @NotNull RequestBuilder request0(String method, String url);

    private static native long create(@NotNull String userAgent) throws IOException;
    private native void drop();
}
