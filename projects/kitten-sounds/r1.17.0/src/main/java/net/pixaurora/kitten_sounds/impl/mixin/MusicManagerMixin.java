package net.pixaurora.kitten_sounds.impl.mixin;

import net.minecraft.client.sounds.MusicManager;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_sounds.impl.service.MusicCompatImpl;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public class MusicManagerMixin {
    @Shadow
    private int nextSongDelay;

    @Inject(
            method = {"<init>", "startPlaying", "stopPlaying"},
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/sounds/MusicManager;nextSongDelay:I", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER)
    )
    private void onNewCooldownSet0(CallbackInfo cInfo) {
        this.updateStartingCooldown();
    }

    @Inject(
            method = "tick",
            at = {
                    @At(value = "FIELD", target = "Lnet/minecraft/client/sounds/MusicManager;nextSongDelay:I", opcode = Opcodes.PUTFIELD, ordinal = 0, shift = At.Shift.AFTER),
                    @At(value = "FIELD", target = "Lnet/minecraft/client/sounds/MusicManager;nextSongDelay:I", opcode = Opcodes.PUTFIELD, ordinal = 1, shift = At.Shift.AFTER)
            }
    )
    private void onNewCooldownSet1(CallbackInfo cInfo) {
        this.updateStartingCooldown();
    }

    private void updateStartingCooldown() {
        KitTunes.updateStartingCooldown(MusicCompatImpl.ticksToMillis(nextSongDelay));
    }
}
