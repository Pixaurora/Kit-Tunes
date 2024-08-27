package net.pixaurora.kit_tunes.api.music;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public interface Track {
    public ResourcePath path();

    public List<String> matches();

    public String name();

    public Artist artist();

    public default Optional<Album> album() {
        if (this.albums().size() > 0) {
            return Optional.of(this.albums().get(new Random().nextInt(this.albums().size())));
        } else {
            return Optional.empty();
        }
    }

    public List<Album> albums();
}
