package net.pixaurora.kitten_heart.impl.ui.screen;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.ReturnToPreviousScreen;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;
import net.pixaurora.kitten_cube.impl.ui.widget.StaticIcon;
import net.pixaurora.kitten_cube.impl.ui.widget.button.RectangularButton;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.scrobble.LastFMScrobbler;
import net.pixaurora.kitten_heart.impl.ui.screen.scrobbler.ScrobblerSetupScreen;

public class KitTunesHomeScreen extends ReturnToPreviousScreen {
    public static final Texture KIT_TUNES_ICON = Texture.of(KitTunes.resource("textures/icon.png"), Size.of(16, 16));

    public static final Component REGISTER_SCROBBLER_LABEL = Component
            .translatable("kit_tunes.home.register_scrobbler");

    public KitTunesHomeScreen(Screen previous) {
        super(previous);
    }

    @Override
    public void firstInit() {
        Point widgetPos = Point.of(0, 0);

        this.addWidget(new StaticIcon(KIT_TUNES_ICON, KIT_TUNES_ICON.size().centerOnVertical(widgetPos)));

        widgetPos = widgetPos.offset(0, KIT_TUNES_ICON.size().y() + 4);
        widgetPos = RectangularButton.DEFAULT_SIZE.centerOnVertical(widgetPos);
        this.addWidget(RectangularButton.vanillaButton(widgetPos, REGISTER_SCROBBLER_LABEL,
                button -> MinecraftClient.setScreen(new ScrobblerSetupScreen<>(this, LastFMScrobbler.TYPE))));
    }

    @Override
    protected AlignmentStrategy alignmentMethod() {
        return Alignment.CENTER;
    }
}
