package net.pixaurora.kit_tunes.api.music;

import java.util.Optional;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public interface Artist {
    public ResourcePath path();

    public String name();

    public Optional<ResourcePath> iconPath();
}
