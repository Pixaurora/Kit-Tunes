package net.pixaurora.kitten_heart.impl.music.history;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.pixaurora.kit_tunes.api.music.history.ListenRecord;

public class ListenHistory {
    private final List<ListenRecord> history;
    private Duration maxRetention;

    public ListenHistory(Duration maxRetention, List<ListenRecord> history) {
        this.maxRetention = maxRetention;
        this.history = history;
    }

    public static ListenHistory defaults() {
        return new ListenHistory(Duration.ofDays(1000), new ArrayList<>());
    }

    public List<ListenRecord> getHistory() {
        return history;
    }

    public Duration maxRetention() {
        return this.maxRetention;
    }

    public void timespanStored(Duration newValue) {
        this.maxRetention = newValue;
        this.clearOldHistory();
    }

    public void record(ListenRecord record) {
        this.history.add(record);
        this.clearOldHistory();
    }

    private void clearOldHistory() {
        Predicate<ListenRecord> isTooOld = record -> Duration
                .between(record.timestamp(), Instant.now())
                .compareTo(this.maxRetention) > 0;

        this.history.removeIf(isTooOld);
    }
}
