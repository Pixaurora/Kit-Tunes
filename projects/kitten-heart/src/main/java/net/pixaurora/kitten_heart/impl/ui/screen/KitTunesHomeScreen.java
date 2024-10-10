package net.pixaurora.kitten_heart.impl.ui.screen;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.StaticGuiTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.button.RectangularButton;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.scrobble.LastFMScrobbler;
import net.pixaurora.kitten_heart.impl.ui.screen.music.MusicScreen;
import net.pixaurora.kitten_heart.impl.ui.screen.scrobbler.ScrobblerSetupScreen;

public class KitTunesHomeScreen extends KitTunesScreenTemplate {
    public static final GuiTexture SPLASH = GuiTexture.of(KitTunes.resource("textures/gui/sprites/logo/main.png"),
            Size.of(272, 64));

    public static final Component REGISTER_SCROBBLER_LABEL = Component
            .translatable("kit_tunes.home.register_scrobbler");
    public static final Component PLAYING_MUSIC_LABEL = Component
            .translatable("kit_tunes.home.music");

    public KitTunesHomeScreen(Screen previous) {
        super(previous);
    }

    @Override
    public void firstInit() {
        Point widgetPos = Point.of(0, -108);

        this.addWidget(new StaticGuiTexture(SPLASH, SPLASH.size().centerHorizontally(widgetPos)));

        widgetPos = widgetPos.offset(0, SPLASH.size().height() + 48);
        widgetPos = RectangularButton.DEFAULT_SIZE.centerHorizontally(widgetPos);
        this.addWidget(RectangularButton.vanillaButton(widgetPos, PLAYING_MUSIC_LABEL,
                button -> MinecraftClient.setScreen(new MusicScreen(this))));

        widgetPos = widgetPos.withX(0).offset(0, RectangularButton.DEFAULT_SIZE.y() + 4);
        widgetPos = RectangularButton.DEFAULT_SIZE.centerHorizontally(widgetPos);
        this.addWidget(RectangularButton.vanillaButton(widgetPos, REGISTER_SCROBBLER_LABEL,
                button -> MinecraftClient.setScreen(new ScrobblerSetupScreen<>(this, LastFMScrobbler.TYPE))));
    }

    @Override
    protected AlignmentStrategy alignmentMethod() {
        return Alignment.CENTER;
    }
}
