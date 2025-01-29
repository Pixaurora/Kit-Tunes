package net.pixaurora.kitten_heart.impl.music.control;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_heart.impl.KitTunes;

public enum PlaybackState {
    PLAYING(KitTunes.resource("textures/gui/sprites/widget/button/icon/play.png")),
    PAUSED(KitTunes.resource("textures/gui/sprites/widget/button/icon/pause.png")),
    STOPPED(KitTunes.resource("textures/gui/sprites/widget/button/icon/stop.png")),
    ;

    private final GuiTexture icon;

    PlaybackState(ResourcePath iconPath) {
        this.icon = GuiTexture.of(iconPath, Size.of(16, 16));
    }

    public GuiTexture icon() {
        return this.icon;
    }

    public boolean canBeChanged() {
        return this != STOPPED;
    }
}
