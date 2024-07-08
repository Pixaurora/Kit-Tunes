package net.pixaurora.kit_tunes.impl.ui.toast;

import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;
import net.pixaurora.kit_tunes.impl.ui.texture.GuiTexture;
import net.pixaurora.kit_tunes.impl.ui.texture.Texture;

public class MeowPlayingToast implements KitTunesToastData {
    public static final Component TITLE = Component.translatable("kit_tunes.toast.title");

    public static final ResourcePath DEFAULT_ICON = KitTunes.resource("textures/album_art/default.png");

    public static final ResourcePath TEXTURE = KitTunes.resource("textures/gui/sprites/toast/loaf.png");

    public static ToastBackground BACKGROUND = new ToastBackground(
            new ToastBackgroundAppearance(GuiTexture.of(TEXTURE, Size.of(43, 24)), Point.of(32, 18), Size.of(2, 2)),
            Point.of(8, 1), Point.of(34, 5), Point.of(34, 19), 180, 8, 4);

    private final Track track;

    public MeowPlayingToast(Track track) {
        this.track = track;
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
}
