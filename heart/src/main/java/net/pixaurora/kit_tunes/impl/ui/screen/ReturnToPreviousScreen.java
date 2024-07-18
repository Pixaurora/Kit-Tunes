package net.pixaurora.kit_tunes.impl.ui.screen;

import net.pixaurora.kit_tunes.impl.ui.MinecraftClient;

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
