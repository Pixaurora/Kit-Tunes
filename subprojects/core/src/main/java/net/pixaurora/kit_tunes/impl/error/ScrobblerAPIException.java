package net.pixaurora.kit_tunes.impl.error;

import net.pixaurora.kit_tunes.impl.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.scrobble.ScrobblerType;

public class ScrobblerAPIException extends KitTunesBaseException {
	private static final long serialVersionUID = 1L;

	private static final ResourcePath MESSAGE = ResourcePath.fromString("kit_tunes.error.scrobbler.api");

	private final ScrobblerType<?> type;

	private final int errorCode;
	private final String explanation;

	public ScrobblerAPIException(ScrobblerType<?> type, int errorCode, String explanation) {
		super("API Exception from " + type.name() + " API: `" + explanation + "` (Error code: " + errorCode + ")");

		this.type = type;
		this.errorCode = errorCode;
		this.explanation = explanation;
	}

	@Override
	public ResourcePath userMessage() {
		return MESSAGE;
	}

	public ScrobblerType<?> api() {
		return this.type;
	}

	public int errorCode() {
		return this.errorCode;
	}

	public String explanation() {
		return this.explanation;
	}
}
