package net.pixaurora.catculator.impl.http;

import net.pixaurora.catculator.api.http.Server;

public class ServerImpl implements Server {
    private final long pointer;

    public ServerImpl() {
        this.pointer = create();
    }

    private static native long create();

    @Override
    public String runServer() {
        return this.runServer0();
    }

    private native String runServer0();

    @Override
    public void close() {
        this.drop();
    }

    private native void drop();
}
