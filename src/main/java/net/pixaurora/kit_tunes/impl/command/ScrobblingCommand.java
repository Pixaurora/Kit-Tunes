package net.pixaurora.kit_tunes.impl.command;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.quiltmc.qsl.command.api.client.QuiltClientCommandSource;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.network.ParsingException;
import net.pixaurora.kit_tunes.impl.scrobble.LastFMScrobbler;
import net.pixaurora.kit_tunes.impl.scrobble.Scrobbler;
import net.pixaurora.kit_tunes.impl.scrobble.ScrobblerType;

import static org.quiltmc.qsl.command.api.client.ClientCommandManager.literal;

public class ScrobblingCommand {
	private static SimpleCommandExceptionType ERROR_CANCELLED = new SimpleCommandExceptionType(Component.translatable("kit_tunes.scrobbler.setup.cancelled"));
	private static SimpleCommandExceptionType ERROR_TIMEOUT = new SimpleCommandExceptionType(Component.translatable("kit_tunes.scrobbler.setup.timeout"));

	public static void register(CommandDispatcher<QuiltClientCommandSource> dispatcher, CommandBuildContext buildContext, Commands.CommandSelection environment) {
		dispatcher.register(
			literal("scrobbler-setup")
				.executes(c -> ScrobblingCommand.setup(c.getSource(), LastFMScrobbler.TYPE))
		);
	}

	public static int setup(QuiltClientCommandSource source, ScrobblerType<?> typeOfScrobbler) throws CommandSyntaxException {
		source.sendFeedback(Component.translatable("kit_tunes.scrobbler.setup.waiting"));

		String url = typeOfScrobbler.setupURL();
		source.sendFeedback(
			Component.literal(url)
				.withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)))
		);

		Scrobbler scrobbler;
		try {
			scrobbler = typeOfScrobbler.setup(5, TimeUnit.MINUTES);
		} catch (InterruptedException interrupted) {
			throw ERROR_CANCELLED.create();
		} catch (TimeoutException timeout) {
			throw ERROR_TIMEOUT.create();
		} catch (ExecutionException | IOException | ParsingException executionError) {
			throw new RuntimeException(executionError);
		}

		KitTunes.SCROBBLER_CACHE.execute(cache -> cache.addScrobbler(scrobbler));
		KitTunes.SCROBBLER_CACHE.save();

		source.sendFeedback(Component.translatable("kit_tunes.scrobbler.setup.success"));

		return 1;
	}
}
