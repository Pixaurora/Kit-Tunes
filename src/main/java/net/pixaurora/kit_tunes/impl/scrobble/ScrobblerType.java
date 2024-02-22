package net.pixaurora.kit_tunes.impl.scrobble;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.mojang.serialization.Codec;

import net.pixaurora.kit_tunes.impl.config.dispatch.DispatchType;
import net.pixaurora.kit_tunes.impl.network.ParsingException;
import net.pixaurora.kit_tunes.impl.network.SetupServer;

public record ScrobblerType<T extends Scrobbler>(
	String name, Codec<T> codec, String setupURL, String tokenPrefix, SetupMethod<T> setupMethod
) implements DispatchType<Scrobbler> {
	public interface SetupMethod<T> {
		public T createScrobbler(String token) throws InterruptedException, IOException, ParsingException;
	}

	public CompletableFuture<T> setup(long timeout, TimeUnit unit) throws IOException {
		return SetupServer.create(this.tokenPrefix)
			.awaitedToken()
			.orTimeout(timeout, unit)
			.thenApply(token -> {
				try {
					return this.setupMethod.createScrobbler(token);
				} catch (InterruptedException | IOException | ParsingException e) {
					throw new RuntimeException(e);
				}
			});
	}
}
