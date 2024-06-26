package net.pixaurora.kit_tunes.impl.ui;

import java.util.HashMap;
import java.util.Map;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.ui.text.Component;

public abstract class ConversionCache<R, C> {
	private final Map<ResourcePath, R> resourceConversions;

	private final Map<Component, C> componentConversions;

	public ConversionCache() {
		this.resourceConversions = new HashMap<>();
		this.componentConversions = new HashMap<>();
	}

	protected abstract R resourceToMinecraftType(ResourcePath path);

	protected abstract C componentToMinecraftType(Component component);

	public R convert(ResourcePath path) {
		return resourceConversions.computeIfAbsent(path, this::resourceToMinecraftType);
	}

	public C convert(Component component) {
		return componentConversions.computeIfAbsent(component, this::componentToMinecraftType);
	}
}