package net.pixaurora.kit_tunes.impl.resource;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public interface TransformsInto<T> {
	public T transform(ResourcePath path);
}
