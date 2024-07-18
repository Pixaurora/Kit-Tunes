package net.pixaurora.kit_tunes.api.music;

import java.util.List;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public interface Album {
    public String name();

    public Optional<ResourcePath> albumArtPath();

    public List<Track> tracks();
}
