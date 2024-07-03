package net.pixaurora.kit_tunes.impl.ui.screen.align;

import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public class CenteredAlignment implements AlignmentStrategy {
    @Override
    public Point align(Point original, Size window) {
        return window.midPoint().offset(original);
    }
}
