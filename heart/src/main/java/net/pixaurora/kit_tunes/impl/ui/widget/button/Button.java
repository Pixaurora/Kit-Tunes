package net.pixaurora.kit_tunes.impl.ui.widget.button;

import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.widget.BasicWidget;

public interface Button extends BasicWidget {
    public boolean isDisabled();

    public void setDisabledStatus(boolean isDisabled);

    @Override
    public default boolean isWithinBounds(Point mousePos) {
        return !this.isDisabled() && BasicWidget.super.isWithinBounds(mousePos);
    }
}
