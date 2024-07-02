package net.pixaurora.kit_tunes.impl.ui.screen;

import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.text.Component;
import net.pixaurora.kit_tunes.impl.ui.texture.Texture;
import net.pixaurora.kit_tunes.impl.ui.widget.StaticIcon;
import net.pixaurora.kit_tunes.impl.ui.widget.button.RectangularButton;

public class KitTunesHomeScreen implements Screen {
    public static final Texture KIT_TUNES_ICON = Texture.of(KitTunes.resource("textures/album_art/default.png"),
            Size.of(16, 16));

    public static final Component REGISTER_SCROBBLER_LABEL = Component
            .translatable("kit_tunes.button.register_scrobbler");

    @Override
    public void init(ScreenHandle handle, Size window) {
        handle.addWidget(new StaticIcon(KIT_TUNES_ICON, window.centerWithinSelf(KIT_TUNES_ICON.size())));

        Point buttonPos = window.divideBy(2).offset(Point.of(0, 40)).toPoint();
        handle.addWidget(RectangularButton.vanillaButton(buttonPos, REGISTER_SCROBBLER_LABEL,
                button -> button.setDisabledStatus(true)));
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
    }
}
