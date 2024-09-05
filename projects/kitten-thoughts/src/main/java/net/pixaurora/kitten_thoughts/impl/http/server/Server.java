package net.pixaurora.kitten_thoughts.impl.http.server;

import java.io.IOException;

public interface Server {
    public static Server create(String tokenArgName) {
        return new ServerImpl(tokenArgName);
    }

    public String runServer() throws IOException;
}
