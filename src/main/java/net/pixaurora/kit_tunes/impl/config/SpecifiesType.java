package net.pixaurora.kit_tunes.impl.config;

public interface SpecifiesType<A extends SpecifiesType<A>> {
	public SerialType<A> type();
}
