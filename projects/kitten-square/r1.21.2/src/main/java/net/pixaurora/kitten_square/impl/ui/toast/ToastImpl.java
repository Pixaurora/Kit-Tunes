package net.pixaurora.kitten_square.impl.ui.toast;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.pixaurora.kitten_square.impl.ui.ConversionCacheImpl;
import net.pixaurora.kitten_square.impl.ui.display.GuiDisplayImpl;

public class ToastImpl implements Toast {
    private final net.pixaurora.kitten_cube.impl.ui.toast.Toast toast;
    private final long millisecondsShown;

    private final ConversionCacheImpl conversions;

    private boolean hasRendered;
    private long firstRenderedTime;

    public ToastImpl(net.pixaurora.kitten_cube.impl.ui.toast.Toast toast) {
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
    public Visibility getWantedVisibility() {
        if (!this.hasRendered) {
            return Toast.Visibility.SHOW;
        }

        long frameTime = System.currentTimeMillis();
        return frameTime - this.firstRenderedTime < this.millisecondsShown ? Toast.Visibility.SHOW
                : Toast.Visibility.HIDE;
    }

    @Override
    public void update(ToastManager toastManager, long time) {
    }

    @Override
    public void render(GuiGraphics guiGraphics, Font font, long time) {
        if (!this.hasRendered) {
            this.hasRendered = true;
            this.firstRenderedTime = System.currentTimeMillis();
        }

        this.toast.draw(new GuiDisplayImpl(guiGraphics, this.conversions));
    }
}
