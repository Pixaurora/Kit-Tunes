package net.pixaurora.kit_tunes.impl.error;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public class ScrobblerSetupTimeoutException extends KitTunesBaseException {
	private static final long serialVersionUID = 1L;

	private static final ResourcePath MESSAGE = ResourcePath.fromString("kit_tunes.error.scrobbler.setup.timeout");

	public ScrobblerSetupTimeoutException() {
		super("Scrobbler setup timed out.");
	}

	@Override
	public ResourcePath userMessage() {
		return MESSAGE;
	}

	@Override
	public boolean isPrinted() {
		return false;
	}
}
