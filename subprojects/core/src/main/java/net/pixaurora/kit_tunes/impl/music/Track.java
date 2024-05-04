package net.pixaurora.kit_tunes.impl.music;

import java.util.List;
import java.util.Optional;

public interface Track {
	public List<String> matches();

	public String name();

	public Artist artist();

	public Optional<Album> album();
}
