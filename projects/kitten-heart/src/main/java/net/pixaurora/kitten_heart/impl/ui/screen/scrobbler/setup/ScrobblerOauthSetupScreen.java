package net.pixaurora.kitten_heart.impl.ui.screen.scrobbler.setup;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.screen.WidgetContainer;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.WidgetAnchor;
import net.pixaurora.kitten_cube.impl.ui.widget.button.Button;
import net.pixaurora.kitten_cube.impl.ui.widget.button.RectangularButton;
import net.pixaurora.kitten_cube.impl.ui.widget.text.PushableTextLines;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.error.KitTunesException;
import net.pixaurora.kitten_heart.impl.error.ScrobblerSetupStartException;
import net.pixaurora.kitten_heart.impl.scrobble.scrobbler.Scrobbler;
import net.pixaurora.kitten_heart.impl.scrobble.setup.ServerAuthSetup;
import net.pixaurora.kitten_heart.impl.scrobble.setup.ServerAuthSetup.SetupProcess;
import net.pixaurora.kitten_heart.impl.ui.screen.KitTunesScreenTemplate;

public class ScrobblerOauthSetupScreen<T extends Scrobbler> extends KitTunesScreenTemplate {
    private static final Component TITLE = Component.translatable("kit_tunes.scrobbler_setup.title");

    private static final Component SETUP_IN_BROWSER = Component
            .translatable("kit_tunes.scrobbler_setup.setup_in_browser");

    private static final Component UNUSUAL_ERROR_ENCOUNTERED = Component.translatable("kit_tunes.unusual_error");

    private static final Component SETUP_STARTED = Component.translatable("kit_tunes.scrobbler_setup.started");
    private static final Component SETUP_COMPLETED = Component.translatable("kit_tunes.scrobbler_setup.success");

    private final ServerAuthSetup<T> setup;

    private Optional<SetupProcess<T>> awaitedScrobbler;
    private Optional<Button> setupInBrowser;
    private Optional<PushableTextLines> setupStatus;

    public ScrobblerOauthSetupScreen(Screen previous, ServerAuthSetup<T> setup) {
        super(previous);

        this.setup = setup;
        this.awaitedScrobbler = Optional.empty();
        this.setupStatus = Optional.empty();
    }

    private void setMessage(Component message) {
        PushableTextLines output = this.setupStatus.orElseThrow(RuntimeException::new);

        output.clear();
        output.push(message);
    }

    private void sendError(KitTunesException exception) {
        PushableTextLines output = this.setupStatus.orElseThrow(RuntimeException::new);

        output.setColor(Color.RED);

        output.push(exception.userMessage());
        if (exception.isPrinted()) {
            KitTunes.LOGGER.error("Unhandled exception during scrobbler setup!", exception);
            output.push(UNUSUAL_ERROR_ENCOUNTERED);
        }
    }

    @Override
    protected void firstInit() {
        Point widgetOffset = Point.of(0, 10);

        WidgetContainer<PushableTextLines> title = this.addWidget(PushableTextLines.regular())
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .at(widgetOffset);
        title.get().push(TITLE);

        String setupUrl = this.setup.url();

        WidgetContainer<RectangularButton> setupInBrowser = this
                .addWidget(
                        RectangularButton.vanillaButton(SETUP_IN_BROWSER, button -> MinecraftClient.openURL(setupUrl)))
                .align(title.relativeTo(WidgetAnchor.BOTTOM_MIDDLE))
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .at(widgetOffset);
        this.setupInBrowser = Optional.of(setupInBrowser.get());

        MinecraftClient.openURL(setupUrl);

        WidgetContainer<PushableTextLines> setupStatus = this.addWidget(PushableTextLines.regular())
                .align(setupInBrowser.relativeTo(WidgetAnchor.BOTTOM_MIDDLE))
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .at(widgetOffset);

        this.setupStatus = Optional.of(setupStatus.get());

        try {
            this.awaitedScrobbler = Optional.of(this.setup.run());

            this.setMessage(SETUP_STARTED);
        } catch (IOException e) {
            this.sendError(new ScrobblerSetupStartException(e));

            this.setupInBrowser.get().setDisabledStatus(true);
        }
    }

    @Override
    protected Alignment alignmentMethod() {
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
            SetupProcess<T> awaitedScrobbler = this.awaitedScrobbler.get();

            if (awaitedScrobbler.isComplete()) {
                try {
                    T scrobbler = awaitedScrobbler.get();
                    this.saveScrobbler(scrobbler);

                    this.setMessage(SETUP_COMPLETED);
                } catch (ExecutionException | InterruptedException | IOException e) {
                    this.sendError(KitTunesException.convert(e));
                }

                this.setupInBrowser.get().setDisabledStatus(true);
                this.awaitedScrobbler = Optional.empty();
            }
        }
    }
}
