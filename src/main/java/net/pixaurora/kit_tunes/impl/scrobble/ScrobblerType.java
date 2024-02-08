package net.pixaurora.kit_tunes.impl.scrobble;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;

import net.pixaurora.kit_tunes.impl.config.dispatch.DispatchType;

public record ScrobblerType<T extends Scrobbler>(
	String name, Codec<T> codec, String setupURL, Supplier<CompletableFuture<T>> setupMethod
) implements DispatchType<Scrobbler> {
	public Future<T> setup() {
		return setupMethod.get();
	}
}
