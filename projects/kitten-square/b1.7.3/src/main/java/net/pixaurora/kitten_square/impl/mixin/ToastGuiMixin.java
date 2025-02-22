package net.pixaurora.kitten_square.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.ToastGui;
import net.minecraft.stat.achievement.AchievementStat;
import net.pixaurora.kitten_square.impl.ui.toast.ToastManager;
import net.pixaurora.kitten_square.impl.ui.toast.DeferredVanillaToast.VanillaToastType;

@Mixin(ToastGui.class)
public class ToastGuiMixin {
    @Shadow
    private long startTime;

    @Shadow
    private boolean tutorial;

    @Inject(method = "set", at = @At("HEAD"), cancellable = true)
    public void onAchievementToastSet(AchievementStat info, CallbackInfo cInfo) {
        this.deferToast(info, cInfo, VanillaToastType.ACHIEVEMENT);
    }

    @Inject(method = "setTutorial", at = @At("HEAD"), cancellable = true)
    public void onTutorialToastSet(AchievementStat info, CallbackInfo cInfo) {
        this.deferToast(info, cInfo, VanillaToastType.TUTORIAL);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void renderOwnToasts(CallbackInfo cInfo) {
        ToastManager.INSTANCE.addVanillaToast((ToastGui) (Object) this);

        if (this.startTime != 0) { // Vanilla toast is showing
            return;
        } else {
            ToastManager.INSTANCE.render();
        }

        if (ToastManager.INSTANCE.isRendering()) {
            cInfo.cancel();
        }
    }

    private void deferToast(AchievementStat info, CallbackInfo cInfo, VanillaToastType toastType) {
        if (ToastManager.INSTANCE.isRendering()) {
            cInfo.cancel();
            ToastManager.INSTANCE.deferVanillaToast(info, toastType);
        }
    }
}
