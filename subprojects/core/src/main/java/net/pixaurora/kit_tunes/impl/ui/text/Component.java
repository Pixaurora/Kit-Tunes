package net.pixaurora.kit_tunes.impl.ui.text;

import net.pixaurora.kit_tunes.impl.KitTunes;

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
}
