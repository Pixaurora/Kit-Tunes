package net.pixaurora.kit_tunes.impl.command;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.quiltmc.qsl.command.api.client.QuiltClientCommandSource;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.scrobble.LastFMScrobbler;
import net.pixaurora.kit_tunes.impl.scrobble.Scrobbler;
import net.pixaurora.kit_tunes.impl.scrobble.ScrobblerSetupException;
import net.pixaurora.kit_tunes.impl.scrobble.ScrobblerType;

import static org.quiltmc.qsl.command.api.client.ClientCommandManager.literal;

public class ScrobblingCommand {
	public static void register(CommandDispatcher<QuiltClientCommandSource> dispatcher, CommandBuildContext buildContext, Commands.CommandSelection environment) {
		dispatcher.register(
			literal("scrobbler-setup")
				.executes(c -> ScrobblingCommand.setup(c.getSource(), LastFMScrobbler.TYPE))
		);
	}

	private static void onSetupError(QuiltClientCommandSource source, ScrobblerSetupException exception) {
		source.sendError(exception.userMessage());

		if (exception.isPrinted()) {
			KitTunes.LOGGER.error("Unhandled exception during Scrobbler Setup!", exception.cause());
		}
	}

	private static int setup(QuiltClientCommandSource source, ScrobblerType<?> typeOfScrobbler) throws CommandSyntaxException {
		source.sendFeedback(Component.translatable("kit_tunes.scrobbler.setup.waiting"));

		String url = typeOfScrobbler.setupURL();
		source.sendFeedback(
			Component.literal(url)
				.withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)))
		);

		CompletableFuture<? extends Scrobbler> awaitedScrobbler;
		try {
			awaitedScrobbler = typeOfScrobbler.setup(5, TimeUnit.MINUTES);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		awaitedScrobbler.whenComplete((scrobbler, error) -> {
			if (error != null) {
				onSetupError(source, ScrobblerSetupException.convert(error));
				return;
			}

			KitTunes.SCROBBLER_CACHE.execute(cache -> cache.addScrobbler(scrobbler));
			KitTunes.SCROBBLER_CACHE.save();

			source.sendFeedback(Component.translatable("kit_tunes.scrobbler.setup.success"));
		});

		return 1;
	}
}
