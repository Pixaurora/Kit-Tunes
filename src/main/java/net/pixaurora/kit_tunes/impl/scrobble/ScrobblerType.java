package net.pixaurora.kit_tunes.impl.scrobble;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

	public T setup(
		long timeoutTime, TimeUnit timeoutUnit
	) throws InterruptedException, IOException, ExecutionException, TimeoutException, ParsingException {
		Future<String> awaitedToken = SetupServer.create(this.tokenPrefix).awaitedToken();

		return setupMethod.createScrobbler(awaitedToken.get(timeoutTime, timeoutUnit));
	}
}
