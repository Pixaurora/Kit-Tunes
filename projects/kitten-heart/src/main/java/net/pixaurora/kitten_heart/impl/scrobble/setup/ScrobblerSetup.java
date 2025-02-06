package net.pixaurora.kitten_heart.impl.scrobble.setup;

import net.pixaurora.kitten_cube.impl.ui.screen.Screen;

@FunctionalInterface
public interface ScrobblerSetup {
    public Screen setupScreen(Screen previous);
}
