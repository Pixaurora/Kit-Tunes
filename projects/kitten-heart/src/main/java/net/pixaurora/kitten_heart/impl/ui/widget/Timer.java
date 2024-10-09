package net.pixaurora.kitten_heart.impl.ui.widget;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;
import net.pixaurora.kitten_cube.impl.ui.widget.text.PushableTextLines;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressProvider;

public class Timer implements Widget {
    private final PushableTextLines text;
    private final ProgressProvider progress;

    private long playedSeconds;
    private final long totalSeconds;

    public Timer(Point pos, ProgressProvider progress) {
        this.text = new PushableTextLines(pos);
        this.progress = progress;
        this.playedSeconds = -1;
        this.totalSeconds = this.progress.totalDuration().getSeconds();
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        this.text.draw(gui, mousePos);
    }

    @Override
    public void tick() {
        long newPlayedSeconds = this.progress.playedDuration().getSeconds();

        if (this.playedSeconds != newPlayedSeconds) {
            this.playedSeconds = newPlayedSeconds;

            this.text.clear();
            this.text.push(Component.literal(this.display()), Color.WHITE);
        }
    }

    private String display() {
        StringBuilder display = new StringBuilder();

        addTimer(display, this.playedSeconds);

        display.append(new char[] { ' ', '/', ' ' });

        addTimer(display, this.totalSeconds);

        return display.toString();
    }

    private void addTimer(StringBuilder builder, long totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        builder.append(Long.toString(minutes));

        builder.append(':');

        if (seconds < 10) {
            builder.append('0');
        }
        builder.append(Long.toString(seconds));
    }

    @Override
    public void onClick(Point mousePos, MouseButton button) {
    }

    @Override
    public boolean isWithinBounds(Point mousePos) {
        return false;
    }
}
