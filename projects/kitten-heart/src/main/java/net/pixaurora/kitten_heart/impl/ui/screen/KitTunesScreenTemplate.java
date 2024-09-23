package net.pixaurora.kitten_heart.impl.ui.screen;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.screen.ReturnToPreviousScreen;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;
import net.pixaurora.kitten_cube.impl.ui.widget.TiledBackground;
import net.pixaurora.kitten_heart.impl.KitTunes;

public abstract class KitTunesScreenTemplate extends ReturnToPreviousScreen {
    public static final Texture backgroundTile = Texture
            .of(KitTunes.resource("textures/gui/sprites/background/tile.png"), Size.of(16, 16));

    public KitTunesScreenTemplate(Screen previous) {
        super(previous);
    }

    @Override
    protected void addBackground() {
        this.addWidget(new TiledBackground(backgroundTile));
    }

}
