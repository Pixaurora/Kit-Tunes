package net.pixaurora.kitten_heart.impl.ui.toast;

import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;
import net.pixaurora.kitten_cube.impl.ui.tile.InnerTileGrid;
import net.pixaurora.kitten_cube.impl.ui.toast.ToastBackground;
import net.pixaurora.kitten_cube.impl.ui.toast.ToastData;
import net.pixaurora.kitten_heart.impl.EventHandling;
import net.pixaurora.kitten_heart.impl.KitTunes;

import static net.pixaurora.kitten_heart.impl.music.metadata.MusicMetadata.asComponent;

public class MeowPlayingToast implements ToastData {
    public static final Component TITLE = Component.translatable("kit_tunes.toast.title");

    public static final ResourcePath DEFAULT_ICON = KitTunes.resource("textures/icon.png");

    public static final ResourcePath TEXTURE = KitTunes.resource("textures/gui/sprites/toast/meow_playing.png");

    public static ToastBackground BACKGROUND = new ToastBackground(
            new InnerTileGrid(GuiTexture.of(TEXTURE, Size.of(32, 32)), Point.of(23, 6), Size.of(6, 9)),
            Point.of(3, 3), Point.of(25, 3), true, Point.of(25, 12), 159, 4, 2);

    private final Track track;
    private final ListeningProgress progress;

    public MeowPlayingToast(Track track, ListeningProgress progress) {
        this.track = track;
        this.progress = progress;
    }

    @Override
    public Texture icon() {
        ResourcePath texturePath = this.track.album().flatMap(Album::albumArtPath).orElse(DEFAULT_ICON);
        return Texture.of(texturePath, Size.of(16, 16));
    }

    @Override
    public Size iconSize() {
        return Size.of(16, 16);
    }

    @Override
    public Component title() {
        return TITLE;
    }

    @Override
    public Color titleColor() {
        return Color.YELLOW;
    }

    @Override
    public List<Component> messageLines() {
        List<Component> lines = new ArrayList<>();

        Component songDescription = asComponent(this.track).concat(Component.literal(" - "))
                .concat(asComponent(this.track.artist()));

        lines.add(songDescription);

        if (this.track.album().isPresent()) {
            lines.add(asComponent(this.track.album().get()));
        }

        return lines;
    }

    @Override
    public Color messageColor() {
        return Color.WHITE;
    }

    @Override
    public ToastBackground background() {
        return BACKGROUND;
    }

    @Override
    public boolean canBeSuperseded() {
        return !EventHandling.isTracking(progress);
    }
}
