package net.pixaurora.kitten_heart.impl.scrobble;

import java.time.Instant;
import java.util.Optional;

public interface ScrobbleInfo {
    public String trackTitle();

    public String artistTitle();

    public Optional<String> albumTitle();

    public Instant startTime();
}
