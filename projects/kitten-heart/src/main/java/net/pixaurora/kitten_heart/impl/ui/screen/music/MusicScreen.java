package net.pixaurora.kitten_heart.impl.ui.screen.music;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.screen.WidgetContainer;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.WidgetAnchor;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;
import net.pixaurora.kitten_cube.impl.ui.widget.StaticTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.button.RectangularButton;
import net.pixaurora.kitten_cube.impl.ui.widget.text.PushableTextLines;
import net.pixaurora.kitten_heart.impl.EventHandling;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.music.control.MusicControls;
import net.pixaurora.kitten_heart.impl.music.control.PlaybackState;
import net.pixaurora.kitten_heart.impl.music.metadata.MusicMetadata;
import net.pixaurora.kitten_heart.impl.music.progress.PlayingSong;
import net.pixaurora.kitten_heart.impl.ui.screen.KitTunesScreenTemplate;
import net.pixaurora.kitten_heart.impl.ui.widget.PauseButton;
import net.pixaurora.kitten_heart.impl.ui.widget.Timer;
import net.pixaurora.kitten_heart.impl.ui.widget.history.HistoryWidget;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.MusicCooldownProgress;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressBar;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressBarTileSet;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressBarTileSets;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressProvider;

public class MusicScreen extends KitTunesScreenTemplate {
    private static final Component TITLE = Component.translatable("kit_tunes.music.title");

    private static final Component WAITING = Component.translatable("kit_tunes.music.waiting");
    private static final Component PLAYING = Component.translatable("kit_tunes.music.playing");

    private static final ProgressBarTileSet FILLED_TILE_SET = tileSet(
            KitTunes.resource("textures/gui/sprites/widget/music/progress_bar/filled.png"));
    private static final ProgressBarTileSet EMPTY_TILE_SET = tileSet(
            KitTunes.resource("textures/gui/sprites/widget/music/progress_bar/empty.png"));

    private static final ResourcePath DEFAULT_ALBUM_ART = KitTunes.resource("textures/icon.png");

    private static final ProgressBarTileSets PLAYING_SONG_TILE_SET = new ProgressBarTileSets(EMPTY_TILE_SET,
            FILLED_TILE_SET);

    Optional<DisplayMode> mode;

    public MusicScreen(Screen previous) {
        super(previous);

        this.mode = Optional.empty();
    }

    @Override
    protected Alignment alignmentMethod() {
        return Alignment.CENTER;
    }

    @Override
    protected void firstInit() {
        this.setupMode();

        WidgetContainer<PushableTextLines> title = this.addWidget(PushableTextLines.title())
                .align(Alignment.CENTER_TOP)
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .at(Point.of(0, 16));

        title.get().push(TITLE);

        this.addWidget(new HistoryWidget(32))
                .anchor(WidgetAnchor.MIDDLE_LEFT)
                .at(Point.of(10, 0));
    }

    @Override
    public void tick() {
        if (!this.mode.isPresent()) {
            return;
        }

        DisplayMode mode = this.mode.get();

        if (!mode.isActive()) {
            mode.cleanup();
            this.setupMode();
        }
    }

    private void setupMode() {
        Optional<PlayingSong> progress = EventHandling.playingSongs().stream().findFirst();

        DisplayMode mode = progress.isPresent() ? this.createMusicDisplay(progress.get()) : this.createWaitingDisplay();
        this.mode = Optional.of(mode);
    }

    private static ProgressBarTileSet tileSet(ResourcePath texturePath) {
        return ProgressBarTileSet.create(
                GuiTexture.of(texturePath, Size.of(12, 4)),
                Point.ZERO, Size.of(4, 4), Point.of(4, 0), Size.of(4, 4), Point.of(8, 0), Size.of(4, 4));
    }

    // TODO: All of the planned display widgets are the exact same, make a system that consolidates all of this widget-creating logic after all the features are ready

    public DisplayMode createMusicDisplay(PlayingSong song) {
        WidgetContainer<ProgressBar> progressBar = this.configProgressBar(song, PLAYING_SONG_TILE_SET);

        WidgetContainer<Timer> timer = this.configTimer(song, progressBar);

        Optional<Album> album = song.track().flatMap(Track::album);

        ResourcePath albumArtTexture = album
                .flatMap(Album::albumArtPath)
                .orElse(DEFAULT_ALBUM_ART);
        WidgetContainer<StaticTexture> albumArt = this
                .addWidget(
                        new StaticTexture(Texture.of(albumArtTexture, Size.of(128, 128))))
                .anchor(WidgetAnchor.MIDDLE_RIGHT)
                .at(Point.of(-10, 0));

        WidgetContainer<PushableTextLines> trackInfo = this.addWidget(PushableTextLines.body())
                .anchor(WidgetAnchor.BOTTOM_MIDDLE)
                .align(albumArt.relativeTo(WidgetAnchor.TOP_MIDDLE))
                .at(Point.of(0, -2));
        trackInfo.get().push(song.track().map(MusicMetadata::asComponent).orElse(PLAYING));

        WidgetContainer<PauseButton> pauseButton = this
                .addWidget(
                        new PauseButton(
                                () -> song.controls().playbackState(),
                                (button) -> {
                                    MusicControls controls = song.controls();

                                    PlaybackState state = controls.playbackState();

                                    if (state == PlaybackState.PAUSED) {
                                        controls.unpause();
                                    } else if (state == PlaybackState.PLAYING) {
                                        controls.pause();
                                    }
                                }))
                .align(progressBar.relativeTo(WidgetAnchor.BOTTOM_LEFT))
                .at(Point.of(0, 1));

        WidgetContainer<?> backButton = this.backIconButton()
                .align(progressBar.relativeTo(WidgetAnchor.BOTTOM_RIGHT))
                .anchor(WidgetAnchor.TOP_RIGHT)
                .at(Point.of(0, 1));

        return new MusicDisplayMode(song, Arrays.asList(progressBar, timer, albumArt, trackInfo, pauseButton, backButton));
    }

    public DisplayMode createWaitingDisplay() {
        ProgressProvider progress = new MusicCooldownProgress();

        WidgetContainer<StaticTexture> waitingIcon = this
                .addWidget(
                        new StaticTexture(Texture.of(DEFAULT_ALBUM_ART, Size.of(128, 128))))
                .anchor(WidgetAnchor.MIDDLE_RIGHT)
                .at(Point.of(-10, 0));

        WidgetContainer<PushableTextLines> waitingText = this.addWidget(PushableTextLines.body())
                .anchor(WidgetAnchor.BOTTOM_MIDDLE)
                .align(waitingIcon.relativeTo(WidgetAnchor.TOP_MIDDLE))
                .at(Point.of(0, -2));
        waitingText.get().push(WAITING);

        WidgetContainer<ProgressBar> progressBar = this.configProgressBar(progress, PLAYING_SONG_TILE_SET);

        WidgetContainer<Timer> timer = this.configTimer(progress, progressBar);

        WidgetContainer<?> backButton = this.backIconButton()
                .align(progressBar.relativeTo(WidgetAnchor.BOTTOM_RIGHT))
                .anchor(WidgetAnchor.TOP_RIGHT)
                .at(Point.of(0, 1));

        return new WaitingDisplayMode(Arrays.asList(progressBar, waitingIcon, waitingText, timer, backButton));
    }

    private WidgetContainer<ProgressBar> configProgressBar(ProgressProvider progress, ProgressBarTileSets tileSets) {
        return this.addWidget(new ProgressBar(progress, tileSets))
                .align(Alignment.CENTER_BOTTOM)
                .at(Point.of(0, -27))
                .anchor(WidgetAnchor.TOP_MIDDLE);
    }

    private WidgetContainer<Timer> configTimer(ProgressProvider progress, WidgetContainer<?> anchoredWidget) {
        return this.addWidget(new Timer(progress))
                .align(anchoredWidget.relativeTo(WidgetAnchor.BOTTOM_MIDDLE))
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .at(Point.of(0, 3));
    }

    private abstract class DisplayMode {
        private final List<WidgetContainer<?>> widgets;

        public DisplayMode(List<WidgetContainer<?>> widgets) {
            this.widgets = widgets;
        }

        abstract boolean isActive();

        void cleanup() {
            for (WidgetContainer<?> widget : this.widgets) {
                MusicScreen.this.removeWidget(widget);
            }
        }
    }

    private class MusicDisplayMode extends DisplayMode {
        private final PlayingSong song;

        MusicDisplayMode(PlayingSong song, List<WidgetContainer<?>> widgets) {
            super(widgets);
            this.song = song;
        }

        @Override
        public boolean isActive() {
            return EventHandling.isTracking(song.progress());
        }
    }

    private class WaitingDisplayMode extends DisplayMode {
        WaitingDisplayMode(List<WidgetContainer<?>> widgets) {
            super(widgets);
        }

        @Override
        public boolean isActive() {
            return !EventHandling.isTrackingAnything();
        }
    }
}
