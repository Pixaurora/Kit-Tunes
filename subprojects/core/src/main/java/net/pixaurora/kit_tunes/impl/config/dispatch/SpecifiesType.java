package net.pixaurora.kit_tunes.impl.config.dispatch;

public interface SpecifiesType<A extends SpecifiesType<A>> {
    public DispatchType<A> type();
}
