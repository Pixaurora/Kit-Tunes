package net.pixaurora.kitten_square.impl.ui.toast;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.Lighting;

import net.minecraft.client.gui.GuiElement;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.toast.Toast;
import net.pixaurora.kitten_square.impl.ui.ConversionCacheImpl;
import net.pixaurora.kitten_square.impl.ui.display.GuiDisplayImpl;

public class ToastManager extends GuiElement {
    public static ToastManager INSTANCE;

    private static final int MAX_HEIGHT = 128;

    private final List<Toast> unseenToasts = new ArrayList<>();
    private final List<ToastRenderer> renderers = new ArrayList<>();
    private final ConversionCacheImpl conversionCache = new ConversionCacheImpl();

    private Size window;

    public ToastManager(Size window) {
        this.window = window;
    }

    public void onWindowUpdate(Size window) {
        this.window = window;
    }

    public void queueToast(Toast toast) {
        this.unseenToasts.add(toast);
    }

    public void render() {
        this.addNewRenderers();

        GuiDisplay display = new GuiDisplayImpl(this, this.conversionCache);
        long frameTime = System.currentTimeMillis();

        Lighting.turnOff();

        this.renderers.removeIf(toast -> {
            boolean shouldRemoveToast = toast.render(display, this.window, frameTime);

            return shouldRemoveToast;
        });
    }

    private void addNewRenderers() {
        this.unseenToasts.removeIf(unseenToast -> {
            int height = this.height();

            if (height > MAX_HEIGHT) {
                return false;
            } else {
                this.renderers.add(new ToastRenderer(unseenToast, height));

                return true;
            }
        });
    }

    private int height() {
        int height = 0;

        for (ToastRenderer renderer : this.renderers) {
            height = Math.max(renderer.height(), height);
        }

        return height;
    }
}
