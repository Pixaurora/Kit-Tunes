package net.pixaurora.kit_tunes.impl.ui.widget.button;

import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.widget.Widget;

public interface Button extends Widget {
    public boolean isDisabled();

    public void setDisabledStatus(boolean isDisabled);

    @Override
    public default boolean isWithinBounds(Point mousePos) {
        return !this.isDisabled() && Widget.super.isWithinBounds(mousePos);
    }
}
