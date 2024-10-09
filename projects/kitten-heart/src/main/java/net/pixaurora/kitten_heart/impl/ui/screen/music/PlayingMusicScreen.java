package net.pixaurora.kitten_heart.impl.ui.screen.music;

import java.util.Optional;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.screen.WidgetContainer;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_heart.impl.EventHandling;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.music.progress.PlayingSong;
import net.pixaurora.kitten_heart.impl.ui.screen.KitTunesScreenTemplate;
import net.pixaurora.kitten_heart.impl.ui.widget.Timer;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressBar;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressBarTileSet;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressBarTileSets;

public class PlayingMusicScreen extends KitTunesScreenTemplate {
    private static final ProgressBarTileSet filledTileSet = tileSet(
            KitTunes.resource("textures/gui/sprites/widget/music/progress_bar/filled.png"));
    private static final ProgressBarTileSet emptyTileSet = tileSet(
            KitTunes.resource("textures/gui/sprites/widget/music/progress_bar/empty.png"));

    private static final ProgressBarTileSets playingSongTileSet = new ProgressBarTileSets(emptyTileSet, filledTileSet);

    Optional<DisplayMode> mode;

    public PlayingMusicScreen(Screen previous) {
        super(previous);

        this.mode = Optional.empty();
    }

    @Override
    protected AlignmentStrategy alignmentMethod() {
        return Alignment.CENTER;
    }

    @Override
    protected void firstInit() {
        this.setupMode();
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

    private static interface DisplayMode {
        boolean isActive();

        void cleanup();
    }

    public DisplayMode createMusicDisplay(PlayingSong song) {
        WidgetContainer<ProgressBar> progressBar = this.addWidget(new ProgressBar(song, playingSongTileSet));
        WidgetContainer<Timer> timer = this.addWidget(new Timer(Point.of(0, -9), song))
                .customizedAlignment(Alignment.CENTER_BOTTOM);

        return new MusicDisplayMode(progressBar, timer, song);
    }

    public DisplayMode createWaitingDisplay() {
        return new WaitingDisplayMode();
    }

    private class MusicDisplayMode implements DisplayMode {
        private final WidgetContainer<ProgressBar> progressBar;
        private final WidgetContainer<Timer> timer;
        private final PlayingSong song;

        MusicDisplayMode(WidgetContainer<ProgressBar> progressBar, WidgetContainer<Timer> timer, PlayingSong song) {
            this.progressBar = progressBar;
            this.timer = timer;
            this.song = song;
        }

        @Override
        public boolean isActive() {
            return EventHandling.isTracking(song.progress());
        }

        @Override
        public void cleanup() {
            PlayingMusicScreen.this.removeWidget(progressBar);
            PlayingMusicScreen.this.removeWidget(timer);
        }
    }

    private class WaitingDisplayMode implements DisplayMode {
        @Override
        public boolean isActive() {
            return !EventHandling.isTrackingAnything();
        }

        @Override
        public void cleanup() {

        }
    }
}
