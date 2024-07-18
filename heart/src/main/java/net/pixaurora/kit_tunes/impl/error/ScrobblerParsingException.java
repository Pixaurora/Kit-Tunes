package net.pixaurora.kit_tunes.impl.error;

import net.pixaurora.kit_tunes.impl.ui.text.Component;

public class ScrobblerParsingException extends KitTunesException {
    private static final long serialVersionUID = 1L;

    public static final Component MESSAGE = Component.translatable("kit_tunes.error.scrobbler.parsing");

    private final String info;

    public ScrobblerParsingException(String info) {
        super(info);

        this.info = info;
    }

    @Override
    public Component userMessage() {
        return MESSAGE;
    }

    public String info() {
        return this.info;
    }
}
