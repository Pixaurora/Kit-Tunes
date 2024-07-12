package net.pixaurora.kit_tunes.impl.ui.toast;

import java.time.Duration;

import net.pixaurora.kit_tunes.impl.ui.display.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public interface Toast {
    public Duration timeShown();

    public void draw(GuiDisplay gui);

    public Size size();

    public static Toast fromData(KitTunesToastData data) {
        return new DrawableToast(data);
    }
}
