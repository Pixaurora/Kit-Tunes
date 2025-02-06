package net.pixaurora.kitten_heart.impl.ui.screen.scrobbler.setup;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.screen.WidgetContainer;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.WidgetAnchor;
import net.pixaurora.kitten_cube.impl.ui.widget.button.RectangularButton;
import net.pixaurora.kitten_cube.impl.ui.widget.text.PushableTextLines;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextField;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.error.KitTunesException;
import net.pixaurora.kitten_heart.impl.scrobble.scrobbler.ListenBrainzScrobbler;
import net.pixaurora.kitten_heart.impl.ui.screen.KitTunesScreenTemplate;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class ListenBrainzSetupScreen extends KitTunesScreenTemplate {
    private @Nullable WidgetContainer<PushableTextLines> status;

    private static final Component TITLE = Component.translatable("kit_tunes.scrobbler_setup.title");

    private static final Component SUCCESS = Component.translatable("kit_tunes.scrobbler_setup.success");
    private static final Component FAILURE = Component.translatable("kit_tunes.scrobbler_setup.failure");

    private static final Component INSTANCE_URL = Component.translatable("kit_tunes.scrobbler_setup.instance_url");
    private static final Component AUTHORIZATION_TOKEN = Component.translatable("kit_tunes.scrobbler_setup.authorization_token");
    private static final Component VALIDATE_CREDENTIALS = Component.translatable("kit_tunes.scrobbler_setup.validate_credentials");

    public ListenBrainzSetupScreen(Screen parent) {
        super(parent);

        this.status = null;
    }

    @Override
    protected Alignment alignmentMethod() {
        return Alignment.CENTER_TOP;
    }

    @Override
    protected void firstInit() {
        Point offset = Point.of(0, 10);
        Component defaultInstanceUrl = Component.literal(ListenBrainzScrobbler.DEFAULT_INSTANCE_URL);

        WidgetContainer<?> title = this.addComponentBox(TITLE, null);

        WidgetContainer<?> instanceUrlHeading = this.addComponentBox(INSTANCE_URL, title);
        WidgetContainer<TextField> instanceUrlField = this.addWidget(TextField.regular(defaultInstanceUrl, 64))
                .align(instanceUrlHeading.relativeTo(WidgetAnchor.BOTTOM_MIDDLE))
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .at(offset);

        WidgetContainer<?> authorizationTokenHeading = this.addComponentBox(AUTHORIZATION_TOKEN, instanceUrlField);
        WidgetContainer<TextField> authorizationTokenField = this.addWidget(TextField.regular(Component.empty(), 64))
                .align(authorizationTokenHeading.relativeTo(WidgetAnchor.BOTTOM_MIDDLE))
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .at(offset);

        WidgetContainer<?> validator = this.addWidget(RectangularButton.vanillaButton(VALIDATE_CREDENTIALS, button -> {
                    String instanceUrl = instanceUrlField.get().input();
                    String authorizationToken = authorizationTokenField.get().input();

                    if (instanceUrl.isEmpty()) {
                        instanceUrl = ListenBrainzScrobbler.DEFAULT_INSTANCE_URL;
                    }

                    try {
                        ListenBrainzScrobbler scrobbler = ListenBrainzScrobbler.fromToken(KitTunes.CLIENT, instanceUrl, authorizationToken);

                        this.saveScrobbler(scrobbler);
                        this.setStatus(SUCCESS, true);
                    } catch (KitTunesException | IOException e) {
                        this.setStatus(FAILURE, false);
                        KitTunes.LOGGER.error("Failed to set up ListenBrainz scrobbler!", e);
                    }
                }))
                .align(authorizationTokenField.relativeTo(WidgetAnchor.BOTTOM_MIDDLE))
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .at(offset);

        this.status = this.addComponentBox(Component.empty(), validator);
    }

    private void setStatus(Component component, boolean success) {
        if (this.status != null) {
            PushableTextLines status = this.status.get();

            status.clear();
            status.push(component);
            status.setColor(success ? Color.BLUE : Color.RED);
        }
    }

    public void saveScrobbler(ListenBrainzScrobbler scrobbler) throws IOException {
        KitTunes.SCROBBLER_CACHE.execute(scrobblers -> scrobblers.addScrobbler(scrobbler));
        KitTunes.SCROBBLER_CACHE.save();
    }

    private WidgetContainer<PushableTextLines> addComponentBox(Component component, @Nullable WidgetContainer<?> previous) {
        Point offset = Point.of(0, 10);
        WidgetContainer<PushableTextLines> widget = this.addWidget(PushableTextLines.regular());

        if (previous == null) {
            widget = widget.anchor(WidgetAnchor.TOP_MIDDLE).at(offset);
        } else {
            widget = widget.align(previous.relativeTo(WidgetAnchor.BOTTOM_MIDDLE)).anchor(WidgetAnchor.TOP_MIDDLE).at(offset);
        }

        widget.get().push(component);
        return widget;
    }
}
