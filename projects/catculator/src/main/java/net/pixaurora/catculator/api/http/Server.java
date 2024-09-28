package net.pixaurora.catculator.api.http;

import java.io.Closeable;
import java.io.IOException;

import net.pixaurora.catculator.impl.http.ServerImpl;

public interface Server extends Closeable {
    public static Server create() {
        return new ServerImpl();
    }

    public String runServer() throws IOException;

    public void close();
}
