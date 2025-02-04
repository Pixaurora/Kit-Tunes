package net.pixaurora.kitten_cube.impl.ui.screen;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.button.RectangularButton;
import net.pixaurora.kitten_heart.impl.KitTunes;

public abstract class ReturnToPreviousScreen extends ScreenTemplate {
    public static final Component BACK_TEXT = Component.translatable("kit_tunes.back");
    public static final GuiTexture BACK_ICON = GuiTexture
            .of(KitTunes.resource("textures/gui/sprites/widget/button/icon/back.png"), Size.of(16, 16));

    private final Screen previous;

    public ReturnToPreviousScreen(Screen previous) {
        this.previous = previous;
    }

    protected void returnToPreviousScreen(boolean runExitHook) {
        if (runExitHook) {
            this.onExit();
        }

        MinecraftClient.setScreen(this.previous);
    }

    @Override
    public void onExit() {
        this.returnToPreviousScreen(false);
    }

    protected WidgetContainer<RectangularButton> backButton() {
        return this.addWidget(
                RectangularButton.vanillaButton(BACK_TEXT, BACK_ICON, button -> returnToPreviousScreen(true)));
    }
}
