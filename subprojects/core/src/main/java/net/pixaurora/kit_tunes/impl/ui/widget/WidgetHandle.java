package net.pixaurora.kit_tunes.impl.ui.widget;

import net.pixaurora.kit_tunes.impl.ui.screen.ScreenHandle;
import net.pixaurora.kit_tunes.impl.ui.sound.Sound;
import net.pixaurora.kit_tunes.impl.ui.sound.SoundPlayer;

public interface WidgetHandle extends SoundPlayer {
    public ScreenHandle screen();

    @Override
    public default void playSound(Sound sound) {
        this.screen().playSound(sound);
    }
}
