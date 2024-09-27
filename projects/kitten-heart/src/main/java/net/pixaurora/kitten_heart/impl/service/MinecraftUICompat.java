package net.pixaurora.kitten_heart.impl.service;

import java.io.IOException;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.text.TextProcessor;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.sound.SoundPlayer;
import net.pixaurora.kitten_cube.impl.ui.toast.Toast;
import net.pixaurora.kitten_heart.impl.resource.temp.FileAccess;

/**
 * The version-specific implementation for pieces of the UI that are organized
 * in the core of the mod.
 */
public interface MinecraftUICompat extends SoundPlayer, TextProcessor {
    public void sendToast(Toast toast);

    public Component translatable(String key);

    public Component translatableWithFallback(String key, String defaultText);

    public Component literal(String text);

    public ResourcePath convertToRegularAsset(ResourcePath path);

    public ResourcePath convertToGuiAsset(ResourcePath path);

    public void setScreen(Screen screen);

    public void openURL(String url);

    public FileAccess accessResource(ResourcePath path) throws IOException;
}
