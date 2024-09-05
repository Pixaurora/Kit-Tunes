package net.pixaurora.kitten_thoughts.impl.http.server;

public class ServerImpl implements Server {
    private final long pointer;

    public ServerImpl(String tokenArgName) {
        this.pointer = create(tokenArgName);
    }

    private static native long create(String tokenArgName);

    @Override
    public String runServer() {
        return this.runServer0();
    }

    private native String runServer0();
}
