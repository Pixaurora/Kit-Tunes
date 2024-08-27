package net.pixaurora.kitten_heart.impl.resource;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public interface TransformsTo<T> {
    public T transform(ResourcePath path);
}
