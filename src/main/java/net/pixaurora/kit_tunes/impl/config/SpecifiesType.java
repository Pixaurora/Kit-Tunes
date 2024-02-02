package net.pixaurora.kit_tunes.impl.config;

public interface SpecifiesType<A> {
	public SerialType<? extends A> type();
}
