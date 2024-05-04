package net.pixaurora.kit_tunes.impl.error;

import net.pixaurora.kit_tunes.impl.resource.ResourcePath;

public class UnhandledScrobblerException extends KitTunesBaseException {
	private static final long serialVersionUID = 1L;

	private static final ResourcePath MESSAGE = ResourcePath.fromString("kit_tunes.error.scrobbler.unhandled");

	public UnhandledScrobblerException(Throwable cause) {
		super(cause.getMessage());

		this.initCause(cause);
	}

	@Override
	public ResourcePath userMessage() {
		return MESSAGE;
	}

	public static <T> T sendErrorsUpstream(ErroringSupplier<T> action) throws UnhandledScrobblerException {
		try {
			return action.run();
		} catch (Throwable e) {
			throw new UnhandledScrobblerException(e);
		}
	}

	public static interface ErroringSupplier<T> {
		public T run() throws Throwable;
	}
}
