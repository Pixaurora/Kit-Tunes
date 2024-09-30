package net.pixaurora.kitten_heart.impl.ui.screen;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.screen.ReturnToPreviousScreen;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.texture.AnimatedGuiTexture;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.TiledBackground;

public abstract class KitTunesScreenTemplate extends ReturnToPreviousScreen {
    public static final GuiTexture backgroundTile = AnimatedGuiTexture.create(50, "kit_tunes",
            "textures/gui/sprites/background/tile_", 64, Size.of(16, 16));

    public KitTunesScreenTemplate(Screen previous) {
        super(previous);
    }

    @Override
    protected void addBackground() {
        this.addWidget(new TiledBackground(backgroundTile));
    }
}
