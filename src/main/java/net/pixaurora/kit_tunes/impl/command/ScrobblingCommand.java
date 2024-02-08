package net.pixaurora.kit_tunes.impl.command;

import static net.minecraft.commands.Commands.literal;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.scrobble.LastFMScrobbler;
import net.pixaurora.kit_tunes.impl.scrobble.Scrobbler;
import net.pixaurora.kit_tunes.impl.scrobble.ScrobblerType;

public class ScrobblingCommand {
	private static SimpleCommandExceptionType ERROR_CANCELLED = new SimpleCommandExceptionType(Component.translatable("kit_tunes.scrobbler.setup.cancelled"));
	private static SimpleCommandExceptionType ERROR_TIMEOUT = new SimpleCommandExceptionType(Component.translatable("kit_tunes.scrobbler.setup.timeout"));

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(
			literal("scrobbler-setup")
				.executes(c -> ScrobblingCommand.setup(c.getSource(), LastFMScrobbler.TYPE))
		);
	}

	public static int setup(CommandSourceStack source, ScrobblerType<?> typeOfScrobbler) throws CommandSyntaxException {
		Future<? extends Scrobbler> awaitedScrobbler = typeOfScrobbler.setup();

		source.sendSuccess(() -> Component.translatable("kit_tunes.scrobbler.setup.waiting"), false);

		String url = typeOfScrobbler.setupURL();
		source.sendSuccess(
			() -> Component.literal(url)
				.withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))),
			false);

		Scrobbler scrobbler;
		try {
			scrobbler = awaitedScrobbler.get(5, TimeUnit.MINUTES);
		} catch (InterruptedException interrupted) {
			throw ERROR_CANCELLED.create();
		} catch (TimeoutException timeout) {
			throw ERROR_TIMEOUT.create();
		} catch (ExecutionException executionError) {
			throw new RuntimeException(executionError);
		}

		KitTunes.SCROBBLER_CACHE.get().addScrobbler(scrobbler);
		KitTunes.SCROBBLER_CACHE.save();

		source.sendSuccess(() -> Component.translatable("kit_tunes.scrobbler.setup.success"), false);

		return 1;
	}
}
