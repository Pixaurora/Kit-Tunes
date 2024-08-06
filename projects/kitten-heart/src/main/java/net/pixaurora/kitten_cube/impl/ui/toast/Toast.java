package net.pixaurora.kitten_cube.impl.ui.toast;

import java.time.Duration;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;

public interface Toast {
    public Duration timeShown();

    public void draw(GuiDisplay gui);

    public Size size();

    public static Toast fromData(ToastData data) {
        return new DrawableToast(data);
    }
}
