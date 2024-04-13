package net.pixaurora.kit_tunes.impl.resource;

import java.nio.file.Path;
import java.util.List;

public interface PathLocatingStrategy {
	public List<Path> getPaths();
}
