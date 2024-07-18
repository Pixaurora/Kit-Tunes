package net.pixaurora.kit_tunes.impl.error;

import net.pixaurora.kit_tunes.impl.ui.text.Component;

public class ScrobblerSetupStartException extends KitTunesException {
    private static final long serialVersionUID = 1L;
    public static final Component MESSAGE = Component.translatable("kit_tunes.error.scrobbler_setup.failed_to_start");

    public ScrobblerSetupStartException(Throwable cause) {
        super("Creation of the scrobbler's setup server failed!");

        this.initCause(cause);
    }

    @Override
    public Component userMessage() {
        return MESSAGE;
    }
}
