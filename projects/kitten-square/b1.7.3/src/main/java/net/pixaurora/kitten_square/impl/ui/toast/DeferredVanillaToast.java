package net.pixaurora.kitten_square.impl.ui.toast;

import java.util.function.BiConsumer;

import net.minecraft.client.gui.ToastGui;
import net.minecraft.stat.achievement.AchievementStat;

public class DeferredVanillaToast {
    private final VanillaToastType toastType;
    private final AchievementStat toastInfo;

    public DeferredVanillaToast(VanillaToastType toastType, AchievementStat toastInfo) {
        this.toastType = toastType;
        this.toastInfo = toastInfo;
    }

    public void queue(ToastGui toastRenderer) {
        this.toastType.toastQueuer.accept(toastRenderer, this.toastInfo);
    }

    public static enum VanillaToastType {
        ACHIEVEMENT(ToastGui::set),
        TUTORIAL(ToastGui::setTutorial);

        private final BiConsumer<ToastGui, AchievementStat> toastQueuer;

        private VanillaToastType(BiConsumer<ToastGui, AchievementStat> toastQueuer) {
            this.toastQueuer = toastQueuer;
        }
    }
}
