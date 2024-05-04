package net.pixaurora.kit_tunes.impl.resource;

public interface TransformsInto<T> {
	public T transform(ResourcePath path);
}
