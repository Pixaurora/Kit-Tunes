package net.pixaurora.kit_tunes.impl.music;

import java.util.Optional;

import net.pixaurora.kit_tunes.impl.resource.NamespacedResourcePath;
import net.pixaurora.kit_tunes.impl.resource.ResourcePath;

public interface Artist {
	public ResourcePath path();

	public String name();

	public Optional<NamespacedResourcePath> imagePath();
}
