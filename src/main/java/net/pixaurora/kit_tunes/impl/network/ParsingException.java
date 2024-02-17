package net.pixaurora.kit_tunes.impl.network;

public class ParsingException extends Exception {
	private static final long serialVersionUID = 1L;

	private final String info;

	public ParsingException(String info) {
		this.info = info;
	}

	public String info() {
		return this.info;
	}
}
