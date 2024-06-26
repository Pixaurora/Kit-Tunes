package net.pixaurora.kit_tunes.impl.ui.screen;

import net.pixaurora.kit_tunes.impl.ui.sound.SoundPlayer;
import net.pixaurora.kit_tunes.impl.ui.text.TextProcessor;
import net.pixaurora.kit_tunes.impl.ui.widget.Widget;

public interface ScreenHandle extends TextProcessor, SoundPlayer {
    public void addWidget(Widget widget);
}
