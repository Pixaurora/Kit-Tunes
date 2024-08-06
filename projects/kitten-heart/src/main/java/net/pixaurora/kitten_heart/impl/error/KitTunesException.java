package net.pixaurora.kitten_heart.impl.error;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import net.pixaurora.kitten_cube.impl.text.Component;

public abstract class KitTunesException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected KitTunesException(String message) {
        super(message);
    }

    public static KitTunesException convert(Throwable error) {
        if (error instanceof CompletionException || error instanceof ExecutionException) {
            error = error.getCause();
        }

        if (error instanceof KitTunesException) {
            return (KitTunesException) error;
        } else if (error instanceof TimeoutException) {
            return new ScrobblerSetupTimeoutException();
        } else {
            return new UnhandledKitTunesException(error);
        }
    }

    public abstract Component userMessage();

    public boolean isPrinted() {
        return true;
    }
}
