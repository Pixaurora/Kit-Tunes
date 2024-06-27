package net.pixaurora.kit_tunes.impl.ui.widget.button;

import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.texture.GuiTexture;

public class ButtonBackground {
    private final GuiTexture unhighlighted;
    private final GuiTexture highlighted;
    private final GuiTexture disabled;

    public ButtonBackground(GuiTexture unhighlighted, GuiTexture highlighted, GuiTexture disabled) {
        this.unhighlighted = unhighlighted;
        this.highlighted = highlighted;
        this.disabled = disabled;
    }

    public GuiTexture texture(boolean isDisabled, boolean isHighlighted) {
        if (isDisabled) {
            return this.disabled;
        } else if (isHighlighted) {
            return this.highlighted;
        } else {
            return this.unhighlighted;
        }
    }

    public Size size() {
        return this.unhighlighted.size();
    }
}
