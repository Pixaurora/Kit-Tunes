package net.pixaurora.kitten_heart.impl.ui.widget.progress;

import java.time.Duration;

public interface ProgressProvider {
    double percentComplete();

    Duration playedDuration();

    Duration totalDuration();
}
