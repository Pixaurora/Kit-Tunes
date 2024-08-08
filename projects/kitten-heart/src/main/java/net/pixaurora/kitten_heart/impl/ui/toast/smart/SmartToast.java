package net.pixaurora.kitten_heart.impl.ui.toast.smart;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.toast.DrawableToast;
import net.pixaurora.kitten_cube.impl.ui.toast.Toast;
import net.pixaurora.kitten_cube.impl.ui.toast.ToastData;
import net.pixaurora.kitten_heart.impl.KitTunes;

public class SmartToast implements Toast {
    private final static List<ToastData> QUEUE = new ArrayList<>();
    private static Optional<SmartToast> UNSEEN_TOAST = Optional.empty();

    public static void queue(ToastData toastData) {
        if (UNSEEN_TOAST.isPresent()) {
            QUEUE.add(toastData);
        } else {
            SmartToast nextToast = new SmartToast(toastData);

            UNSEEN_TOAST = Optional.of(nextToast);
            KitTunes.UI_LAYER.sendToast(nextToast);
        }
    }

    private ToastData toastData;
    private Toast currentToast;

    private SmartToast(ToastData toastData) {
        this.toastData = toastData;

        this.currentToast = new DrawableToast(toastData);
    }

    @Override
    public Duration timeShown() {
        return this.currentToast.timeShown();
    }

    @Override
    public void draw(GuiDisplay gui) {
        if (UNSEEN_TOAST.isPresent() && this == UNSEEN_TOAST.get()) {
            this.processQueue();
        }

        this.currentToast.draw(gui);
    }

    @Override
    public Size size() {
        return this.currentToast.size();
    }

    private void processQueue() {
        UNSEEN_TOAST = Optional.empty();

        Optional<ToastData> nextToast = getNextToast();

        if (this.toastData.canBeSuperseded() && nextToast.isPresent()) {
            this.currentToast = new DrawableToast(nextToast.get());

            nextToast = getNextToast();
        }

        if (nextToast.isPresent()) {
            queue(nextToast.get());
        }
    }

    private static Optional<ToastData> getNextToast() {
        ArrayList<ToastData> removedToasts = new ArrayList<>();
        Optional<ToastData> nextToast = Optional.empty();

        for (ToastData toast : QUEUE) {
            nextToast = Optional.of(toast);
            removedToasts.add(toast);

            if (!toast.canBeSuperseded()) {
                break;
            }
        }

        QUEUE.removeAll(removedToasts);

        return nextToast;
    }
}
