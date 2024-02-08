package net.pixaurora.kit_tunes.impl.config.dispatch;

import com.mojang.serialization.Codec;

public interface DispatchType<A extends SpecifiesType<A>> {
	public String name();

	public Codec<? extends A> codec();
}
