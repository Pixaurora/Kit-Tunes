package net.pixaurora.kitten_cube.impl.text;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_heart.impl.KitTunes;

public interface Component {
    public static Component literal(String text) {
        return KitTunes.UI_LAYER.literal(text);
    }

    public static Component translatable(String key) {
        return KitTunes.UI_LAYER.translatable(key);
    }

    public static Component translatableWithFallback(String key, String fallbackText) {
        return KitTunes.UI_LAYER.translatableWithFallback(key, fallbackText);
    }

    public Component concat(Component other);

    public default Size size() {
        return MinecraftClient.textSize(this);
    }
}
