package net.pixaurora.catculator.api.http;

import net.pixaurora.catculator.impl.http.ClientImpl;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface Client extends AutoCloseable {
    static @NotNull Client create(String userAgent) throws IOException {
        return new ClientImpl(userAgent);
    }

    @NotNull RequestBuilder get(String url);
    @NotNull RequestBuilder post(String url);
    @NotNull RequestBuilder request(String method, String url);

    @Override
    void close(); // Remove throws Exception from AutoCloseable
}
