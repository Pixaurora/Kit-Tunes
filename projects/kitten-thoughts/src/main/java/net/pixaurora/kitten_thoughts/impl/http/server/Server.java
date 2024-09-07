package net.pixaurora.kitten_thoughts.impl.http.server;

import java.io.Closeable;
import java.io.IOException;

public interface Server extends Closeable {
    public static Server create() {
        return new ServerImpl();
    }

    public String runServer() throws IOException;

    public void close();
}
