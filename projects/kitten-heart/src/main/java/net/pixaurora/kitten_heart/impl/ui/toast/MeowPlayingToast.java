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
import net.pixaurora.kitten_cube.impl.ui.toast.ToastBackground;
import net.pixaurora.kitten_cube.impl.ui.toast.ToastBackgroundAppearance;
import net.pixaurora.kitten_cube.impl.ui.toast.ToastData;
import net.pixaurora.kitten_heart.impl.EventHandling;
import net.pixaurora.kitten_heart.impl.KitTunes;

public class MeowPlayingToast implements ToastData {
    public static final Component TITLE = Component.translatable("kit_tunes.toast.title");

    public static final ResourcePath DEFAULT_ICON = KitTunes.resource("textures/album_art/default.png");

    public static final ResourcePath TEXTURE = KitTunes.resource("textures/gui/sprites/toast/loaf.png");

    public static ToastBackground BACKGROUND = new ToastBackground(
            new ToastBackgroundAppearance(GuiTexture.of(TEXTURE, Size.of(43, 24)), Point.of(32, 18), Size.of(2, 2)),
            Point.of(8, 1), Point.of(34, 5), Point.of(34, 19), 180, 8, 4);

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

        Component songDescription = Component.literal(this.track.name()).concat(Component.literal(" - "))
                .concat(Component.literal(this.track.artist().name()));

        lines.add(songDescription);

        if (this.track.album().isPresent()) {
            Album album = this.track.album().get();

            lines.add(Component.literal(album.name()));
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
