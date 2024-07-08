package net.pixaurora.kit_tunes.impl.service;

import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.ui.screen.Screen;
import net.pixaurora.kit_tunes.impl.ui.sound.SoundPlayer;
import net.pixaurora.kit_tunes.impl.ui.text.Component;
import net.pixaurora.kit_tunes.impl.ui.text.TextProcessor;
import net.pixaurora.kit_tunes.impl.ui.toast.MeowPlayingToast;
import net.pixaurora.kit_tunes.impl.ui.toast.Toast;

/**
 * The version-specific implementation for pieces of the UI that are organized
 * in the core of the mod.
 */
public interface KitTunesMinecraftUICompat extends SoundPlayer, TextProcessor {
    public static void sendNowPlayingNotification(Track track) {
        KitTunes.UI_LAYER.sendToast(Toast.fromData(new MeowPlayingToast(track)));
    }

    public void sendToast(Toast toast);

    public Component translatable(String key);

    public Component translatableWithFallback(String key, String defaultText);

    public Component literal(String text);

    public ResourcePath convertToRegularAsset(ResourcePath path);

    public ResourcePath convertToGuiAsset(ResourcePath path);

    public void setScreen(Screen screen);

    public void confirmURL(String url);
}
