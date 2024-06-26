package net.pixaurora.kit_tunes.impl.ui.screen;

import net.pixaurora.kit_tunes.impl.ui.Drawable;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public interface Screen extends Drawable<ScreenHandle> {
    public void init(ScreenHandle handle, Size window);
}
