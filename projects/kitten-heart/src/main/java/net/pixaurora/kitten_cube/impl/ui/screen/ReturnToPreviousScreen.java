package net.pixaurora.kitten_cube.impl.ui.screen;

import net.pixaurora.kitten_cube.impl.MinecraftClient;

public abstract class ReturnToPreviousScreen extends ScreenTemplate {
    private final Screen previous;

    public ReturnToPreviousScreen(Screen previous) {
        this.previous = previous;
    }

    @Override
    public void onExit() {
        MinecraftClient.setScreen(this.previous);
    }
}
