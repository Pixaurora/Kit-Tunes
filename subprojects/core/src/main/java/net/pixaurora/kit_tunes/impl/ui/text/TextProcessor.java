package net.pixaurora.kit_tunes.impl.ui.text;

import net.pixaurora.kit_tunes.impl.ui.math.Size;

public interface TextProcessor {
    public int textHeight();

    public int textWidth(Component text);

    public default Size textSize(Component text) {
        return Size.of(this.textWidth(text), this.textHeight());
    }

    public default Size textSize(Component... lines) {
        int width = 0;

        for (Component line : lines) {
            width = Math.max(width, this.textWidth(line));
        }

        return Size.of(width, this.textHeight() * lines.length);
    }
}
