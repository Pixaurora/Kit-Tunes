package net.pixaurora.kit_tunes.impl.error;

import net.pixaurora.kit_tunes.impl.ui.text.Component;

public class UnhandledKitTunesException extends KitTunesException {
    private static final long serialVersionUID = 1L;

    private static final Component MESSAGE = Component.translatable("kit_tunes.error.unhandled");

    public UnhandledKitTunesException(Throwable cause) {
        super(cause.getMessage());

        this.initCause(cause);
    }

    public UnhandledKitTunesException(String message) {
        super(message);
    }

    @Override
    public Component userMessage() {
        return MESSAGE;
    }

    public static <T> T runOrThrow(ErroringSupplier<T> action) throws UnhandledKitTunesException {
        try {
            return action.run();
        } catch (Throwable e) {
            throw new UnhandledKitTunesException(e);
        }
    }

    public static interface ErroringSupplier<T> {
        public T run() throws Throwable;
    }
}
