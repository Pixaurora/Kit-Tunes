package net.pixaurora.kit_tunes.impl.error;

import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeoutException;

import net.pixaurora.kit_tunes.impl.resource.ResourcePath;

public abstract class KitTunesBaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	protected KitTunesBaseException(String message) {
		super(message);
	}

	public static KitTunesBaseException convert(Throwable error) {
		if (error instanceof CompletionException) {
			error = error.getCause();
		}

		if (error instanceof KitTunesBaseException) {
			return (KitTunesBaseException) error;
		} else if (error instanceof TimeoutException) {
			return new ScrobblerSetupTimeoutException();
		} else {
			return new UnhandledScrobblerException(error);
		}
	}

	public abstract ResourcePath userMessage();

	public boolean isPrinted() {
		return true;
	}
}
