package net.pixaurora.kitten_cube.impl.text;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_heart.impl.KitTunes;

public interface Component {
    static Component empty() {
        // TODO: Maybe improve this, modern Minecraft has proper empty components
        return literal("");
    }

    static Component literal(String text) {
        return KitTunes.UI_LAYER.literal(text);
    }

    static Component translatable(String key) {
        return KitTunes.UI_LAYER.translatable(key);
    }

    static Component translatableWithFallback(String key, String fallbackText) {
        return KitTunes.UI_LAYER.translatableWithFallback(key, fallbackText);
    }

    Component concat(Component other);

    default Size size() {
        return MinecraftClient.textSize(this);
    }
}
