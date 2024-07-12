package net.pixaurora.kit_tunes.impl.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;

public class ToastImpl implements Toast {
    private final net.pixaurora.kit_tunes.impl.ui.toast.Toast toast;
    private final long millisecondsShown;

    private final ConversionCacheImpl conversions;

    private boolean hasRendered;
    private long firstRenderedTime;

    public ToastImpl(net.pixaurora.kit_tunes.impl.ui.toast.Toast toast) {
        this.toast = toast;
        this.millisecondsShown = toast.timeShown().toMillis();
        this.conversions = new ConversionCacheImpl();
    }

    @Override
    public int width() {
        return this.toast.size().width();
    }

    @Override
    public int height() {
        return this.toast.size().height();
    }

    @Override
    public Toast.Visibility render(GuiGraphics graphics, ToastComponent manager, long frameTime) {
        if (!this.hasRendered) {
            this.hasRendered = true;
            this.firstRenderedTime = frameTime;
        }

        this.toast.draw(new GuiDisplayImpl(graphics, conversions));

        return frameTime - this.firstRenderedTime < this.millisecondsShown ? Toast.Visibility.SHOW
                : Toast.Visibility.HIDE;
    }
}
