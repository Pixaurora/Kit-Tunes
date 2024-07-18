package net.pixaurora.kitten_heart.impl.config.dispatch;

public interface DispatchType<A extends SpecifiesType<A>> {
    public String name();

    public Class<? extends A> targetClass();
}
