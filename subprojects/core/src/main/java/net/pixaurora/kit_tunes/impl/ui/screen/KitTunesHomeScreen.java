package net.pixaurora.kit_tunes.impl.ui.screen;

import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.scrobble.LastFMScrobbler;
import net.pixaurora.kit_tunes.impl.ui.MinecraftClient;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.screen.align.Alignment;
import net.pixaurora.kit_tunes.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kit_tunes.impl.ui.screen.scrobbler.ScrobblerSetupScreen;
import net.pixaurora.kit_tunes.impl.ui.text.Component;
import net.pixaurora.kit_tunes.impl.ui.texture.Texture;
import net.pixaurora.kit_tunes.impl.ui.widget.StaticIcon;
import net.pixaurora.kit_tunes.impl.ui.widget.button.RectangularButton;

public class KitTunesHomeScreen extends ReturnToPreviousScreen {
    public static final Texture KIT_TUNES_ICON = Texture.of(KitTunes.resource("textures/icon.png"), Size.of(16, 16));

    public static final Component REGISTER_SCROBBLER_LABEL = Component
            .translatable("kit_tunes.home.register_scrobbler");

    public KitTunesHomeScreen(Screen previous) {
        super(previous);
    }

    @Override
    public void firstInit() {
        this.addWidget(new StaticIcon(KIT_TUNES_ICON, KIT_TUNES_ICON.size().centerOn(Point.of(0, 0))));

        Point buttonPos = Point.of(0, 20);
        this.addWidget(RectangularButton.vanillaButton(buttonPos, REGISTER_SCROBBLER_LABEL,
                button -> MinecraftClient.setScreen(new ScrobblerSetupScreen<>(this, LastFMScrobbler.TYPE))));
    }

    @Override
    protected AlignmentStrategy alignmentMethod() {
        return Alignment.CENTER;
    }
}
