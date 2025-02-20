package net.pixaurora.kitten_heart.impl.ui.screen;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.screen.WidgetContainer;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.WidgetAnchor;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.StaticGuiTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.button.RectangularButton;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.scrobble.scrobbler.LastFMScrobbler;
import net.pixaurora.kitten_heart.impl.scrobble.scrobbler.ListenBrainzScrobbler;
import net.pixaurora.kitten_heart.impl.ui.screen.music.MusicScreen;

public class KitTunesHomeScreen extends KitTunesScreenTemplate {
    public static final GuiTexture SPLASH = GuiTexture.of(KitTunes.resource("textures/gui/sprites/logo/main.png"),
            Size.of(272, 64));

    public static final Component REGISTER_LASTFM_SCROBBLER_LABEL = Component
            .translatable("kit_tunes.home.register_scrobbler.lastfm");
    public static final Component REGISTER_LISTENBRAINZ_SCROBBLER_LABEL = Component
            .translatable("kit_tunes.home.register_scrobbler.listenbrainz");
    public static final Component PLAYING_MUSIC_LABEL = Component
            .translatable("kit_tunes.home.music");

    public KitTunesHomeScreen(Screen previous) {
        super(previous);
    }

    @Override
    public void firstInit() {
        WidgetContainer<?> title = this
                .addWidget(new StaticGuiTexture(SPLASH))
                .at(Point.of(0, -108))
                .anchor(WidgetAnchor.TOP_MIDDLE);

        WidgetContainer<?> musicButton = this
                .addWidget(RectangularButton.vanillaButton(PLAYING_MUSIC_LABEL,
                        button -> MinecraftClient.setScreen(new MusicScreen(this))))
                .align(title.relativeTo(WidgetAnchor.BOTTOM_MIDDLE))
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .at(Point.of(0, 48));

        WidgetContainer<?> lastFMButton = this
                .addWidget(RectangularButton.vanillaButton(REGISTER_LASTFM_SCROBBLER_LABEL,
                        button -> MinecraftClient.setScreen(LastFMScrobbler.TYPE.setup().get().setupScreen(this))))
                .align(musicButton.relativeTo(WidgetAnchor.BOTTOM_MIDDLE))
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .at(Point.of(0, 4));

        WidgetContainer<?> listenBrainzButton = this
                .addWidget(RectangularButton.vanillaButton(REGISTER_LISTENBRAINZ_SCROBBLER_LABEL,
                        button -> MinecraftClient.setScreen(ListenBrainzScrobbler.TYPE.setup().get().setupScreen(this))))
                .align(lastFMButton.relativeTo(WidgetAnchor.BOTTOM_MIDDLE))
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .at(Point.of(0, 4));

        this.backButton()
                .align(listenBrainzButton.relativeTo(WidgetAnchor.BOTTOM_MIDDLE))
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .at(Point.of(0, 8));

    }

    @Override
    protected Alignment alignmentMethod() {
        return Alignment.CENTER;
    }
}
