package net.pixaurora.kit_tunes.impl.scrobble;

import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeoutException;

import net.minecraft.network.chat.Component;

public class ScrobblerSetupException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final Throwable cause;
	private final Type type;

	public ScrobblerSetupException(Throwable cause, Type type) {
		this.cause = cause;
		this.type = type;
	}

	public static ScrobblerSetupException convert(Throwable error) {
		if (error instanceof CompletionException) {
			error = error.getCause();
		}

		if (error instanceof ScrobblerSetupException narrowedException) {
			return narrowedException;
		} else if (error instanceof TimeoutException) {
			return new ScrobblerSetupException(error, Type.TIMEOUT);
		} else {
			return new ScrobblerSetupException(error, Type.UNHANDLED);
		}
	}

	public Throwable cause() {
		return this.cause;
	}

	public Component userMessage() {
		return this.type.userMessage;
	}

	public boolean isPrinted() {
		return this.type.isPrinted;
	}

	public static enum Type {
		TIMEOUT(Component.translatable("kit_tunes.scrobbler.setup.error.timeout"), false),
		UNHANDLED(Component.translatable("kit_tunes.scrobbler.setup.error.unhandled"), true),
		;

		private final Component userMessage;
		private final boolean isPrinted;

		Type(Component userMessage, boolean shouldPrintStackTrace) {
			this.userMessage = userMessage;
			this.isPrinted = shouldPrintStackTrace;
		}
	}
}
