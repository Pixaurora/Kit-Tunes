package net.pixaurora.kitten_heart.impl.music.metadata;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.music.Track;

public interface MutableMusicMetadata {
    public void add(Album album);

    public void add(Artist artist);

    public void add(Track track);
}
