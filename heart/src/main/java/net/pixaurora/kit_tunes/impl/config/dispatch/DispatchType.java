package net.pixaurora.kit_tunes.impl.config.dispatch;

public interface DispatchType<A extends SpecifiesType<A>> {
    public String name();

    public Class<? extends A> targetClass();
}
