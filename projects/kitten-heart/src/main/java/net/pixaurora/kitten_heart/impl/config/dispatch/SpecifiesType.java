package net.pixaurora.kitten_heart.impl.config.dispatch;

public interface SpecifiesType<A extends SpecifiesType<A>> {
    public DispatchType<A> type();
}
