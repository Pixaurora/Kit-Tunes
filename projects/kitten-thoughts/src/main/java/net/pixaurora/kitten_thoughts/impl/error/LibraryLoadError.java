package net.pixaurora.kitten_thoughts.impl.error;

public class LibraryLoadError extends Error {
    public LibraryLoadError(String message) {
        super(message);
    }

    public LibraryLoadError(Throwable exception) {
        super(exception);
    }
}
