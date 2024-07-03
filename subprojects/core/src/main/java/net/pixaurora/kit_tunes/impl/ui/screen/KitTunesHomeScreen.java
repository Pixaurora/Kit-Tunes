package net.pixaurora.kit_tunes.impl.ui.screen;

import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.screen.align.Alignment;
import net.pixaurora.kit_tunes.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kit_tunes.impl.ui.text.Component;
import net.pixaurora.kit_tunes.impl.ui.texture.Texture;
import net.pixaurora.kit_tunes.impl.ui.widget.StaticIcon;
import net.pixaurora.kit_tunes.impl.ui.widget.button.RectangularButton;

public class KitTunesHomeScreen extends ScreenTemplate {
    public static final Texture KIT_TUNES_ICON = Texture.of(KitTunes.resource("textures/album_art/default.png"),
            Size.of(16, 16));

    public static final Component REGISTER_SCROBBLER_LABEL = Component
            .translatable("kit_tunes.button.register_scrobbler");

    @Override
    public void firstInit() {
        this.addWidget(new StaticIcon(KIT_TUNES_ICON, KIT_TUNES_ICON.size().centerOn(Point.of(0, 0))));

        Point buttonPos = Point.of(0, 20);
        this.addWidget(RectangularButton.vanillaButton(buttonPos, REGISTER_SCROBBLER_LABEL,
                button -> button.setDisabledStatus(true)));
    }

    @Override
    protected AlignmentStrategy alignmentMethod() {
        return Alignment.CENTER;
    }
}
