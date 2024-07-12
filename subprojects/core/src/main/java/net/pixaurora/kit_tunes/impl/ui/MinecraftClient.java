package net.pixaurora.kit_tunes.impl.ui;

import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.service.KitTunesMinecraftUICompat;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.screen.Screen;
import net.pixaurora.kit_tunes.impl.ui.sound.Sound;
import net.pixaurora.kit_tunes.impl.ui.text.Component;

public interface MinecraftClient {
    private static KitTunesMinecraftUICompat impl() {
        return KitTunes.UI_LAYER;
    }

    public static int textHeight() {
        return impl().textHeight();
    }

    public static int textWidth(Component text) {
        return impl().textWidth(text);
    }

    public static Size textSize(Component text) {
        return impl().textSize(text);
    }

    public static Size textSize(Component... text) {
        return impl().textSize(text);
    }

    public static void playSound(Sound sound) {
        impl().playSound(sound);
    }

    public static void setScreen(Screen screen) {
        impl().setScreen(screen);
    }

    public static void confirmURL(String url) {
        impl().confirmURL(url);
    }
}
