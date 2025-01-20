package net.pixaurora.kitten_cube.impl.ui.widget.event;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;

public class WindowUpdateEventImpl implements WindowUpdateEvent {
    private final Size newWindow;
    private final Screen screen;

    public WindowUpdateEventImpl(Size newWindow, Screen screen) {
        this.newWindow = newWindow;
        this.screen = screen;
    }

    @Override
    public Size newWindow() {
        return this.newWindow;
    }

    @Override
    public Screen screen() {
        return this.screen;
    }
}
