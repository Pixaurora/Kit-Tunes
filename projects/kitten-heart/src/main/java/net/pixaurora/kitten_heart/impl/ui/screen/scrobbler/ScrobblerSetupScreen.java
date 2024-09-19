package net.pixaurora.kitten_heart.impl.ui.screen.scrobbler;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.ReturnToPreviousScreen;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kitten_cube.impl.ui.widget.button.Button;
import net.pixaurora.kitten_cube.impl.ui.widget.button.RectangularButton;
import net.pixaurora.kitten_cube.impl.ui.widget.text.PushableTextLines;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.error.KitTunesException;
import net.pixaurora.kitten_heart.impl.error.ScrobblerSetupStartException;
import net.pixaurora.kitten_heart.impl.scrobble.Scrobbler;
import net.pixaurora.kitten_heart.impl.scrobble.ScrobblerSetup;
import net.pixaurora.kitten_heart.impl.scrobble.ScrobblerType;

public class ScrobblerSetupScreen<T extends Scrobbler> extends ReturnToPreviousScreen {
    private static final Component TITLE = Component.translatable("kit_tunes.scrobbler_setup.title");

    private static final Component SETUP_IN_BROWSER = Component
            .translatable("kit_tunes.scrobbler_setup.setup_in_browser");

    private static final Component UNUSUAL_ERROR_ENCOUNTERED = Component.translatable("kit_tunes.unusual_error");

    private static final Component SETUP_STARTED = Component.translatable("kit_tunes.scrobbler_setup.started");
    private static final Component SETUP_COMPLETED = Component.translatable("kit_tunes.scrobbler_setup.success");

    private final ScrobblerType<T> scrobblerType;

    private Optional<ScrobblerSetup<T>> awaitedScrobbler;
    private Optional<Button> setupInBrowser;
    private Optional<PushableTextLines> setupStatus;

    public ScrobblerSetupScreen(Screen previous, ScrobblerType<T> scrobblerType) {
        super(previous);

        this.scrobblerType = scrobblerType;
        this.awaitedScrobbler = Optional.empty();
        this.setupStatus = Optional.empty();
    }

    private void sendMessage(Component message) {
        this.setupStatus.get().push(message, Color.WHITE);
    }

    private void sendError(KitTunesException exception) {
        PushableTextLines output = this.setupStatus.get();

        output.push(exception.userMessage(), Color.RED);
        if (exception.isPrinted()) {
            KitTunes.LOGGER.error("Unhandled exception during scrobbler setup!", exception);
            output.push(UNUSUAL_ERROR_ENCOUNTERED, Color.RED);
        }
    }

    @Override
    protected void firstInit() {
        Point widgetPos = Point.of(0, 10);
        PushableTextLines title = this.addWidget(new PushableTextLines(widgetPos));
        title.push(TITLE, Color.WHITE);

        widgetPos = title.endPos().offset(0, 10);
        Button setupInBrowser = this
                .addWidget(RectangularButton.vanillaButton(RectangularButton.DEFAULT_SIZE.centerHorizontally(widgetPos),
                        SETUP_IN_BROWSER, button -> MinecraftClient.confirmURL(this.scrobblerType.setupURL())));
        this.setupInBrowser = Optional.of(setupInBrowser);

        widgetPos = widgetPos.offset(0, RectangularButton.DEFAULT_SIZE.y() + 10);
        this.setupStatus = Optional.of(this.addWidget(new PushableTextLines(widgetPos)));

        try {
            this.awaitedScrobbler = Optional.of(this.scrobblerType.setup(5, TimeUnit.MINUTES));

            this.sendMessage(SETUP_STARTED);
        } catch (IOException e) {
            this.sendError(new ScrobblerSetupStartException(e));

            this.setupInBrowser.get().setDisabledStatus(true);
        }
    }

    @Override
    protected AlignmentStrategy alignmentMethod() {
        return Alignment.CENTER_TOP;
    }

    @Override
    public void onExit() {
        if (this.awaitedScrobbler.isPresent()) {
            this.awaitedScrobbler.get().cancel();
        }

        super.onExit();
    }

    public void saveScrobbler(T scrobbler) throws IOException {
        KitTunes.SCROBBLER_CACHE.execute(scrobblers -> scrobblers.addScrobbler(scrobbler));
        KitTunes.SCROBBLER_CACHE.save();
    }

    @Override
    public void tick() {
        if (this.awaitedScrobbler.isPresent()) {
            ScrobblerSetup<T> awaitedScrobbler = this.awaitedScrobbler.get();

            if (awaitedScrobbler.isComplete()) {
                try {
                    T scrobbler = awaitedScrobbler.get();
                    this.saveScrobbler(scrobbler);

                    this.sendMessage(SETUP_COMPLETED);
                } catch (ExecutionException | InterruptedException | IOException e) {
                    this.sendError(KitTunesException.convert(e));
                }

                this.setupInBrowser.get().setDisabledStatus(true);
                this.awaitedScrobbler = Optional.empty();
            }
        }
    }
}
