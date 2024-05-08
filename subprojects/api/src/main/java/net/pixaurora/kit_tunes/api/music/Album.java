package net.pixaurora.kit_tunes.api.music;

import java.util.List;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.resource.NamespacedResourcePath;

public interface Album {
	public String name();

	public Optional<NamespacedResourcePath> albumArtPath();

	public List<Track> tracks();
}
