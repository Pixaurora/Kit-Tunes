package net.pixaurora.kitten_cube.impl.ui.widget.event;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;

public interface WindowUpdateEvent {
    public Size newWindow();

    public Screen screen();
}
