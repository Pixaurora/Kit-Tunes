package net.pixaurora.kit_tunes.impl.resource;

import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonParseException;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public class ResourcePathImpl implements ResourcePath {
	private final String namespace;
	private final List<String> path;

	public ResourcePathImpl(String namespace, List<String> path) {
		this.namespace = namespace;
		this.path = path;
	}

	public ResourcePathImpl(String namespace, String path, String dirSeparator) {
		this.namespace = namespace;
		this.path = parsePath(path, dirSeparator);
	}

	public ResourcePathImpl(String namespace, String path) {
		this(namespace, path, DEFAULT_DIR_SEPARATOR);
	}

	public static ResourcePath fromString(String text, String namespaceSeparator, String dirSeparator) throws JsonParseException {
		String[] parts = text.split(namespaceSeparator, 2);

		if (parts.length != 2) {
			throw new JsonParseException("Resource Paths require a `" + namespaceSeparator + "` to be present!");
		}

		return new ResourcePathImpl(parts[0], parsePath(parts[1], dirSeparator));
	}

	public static final List<String> parsePath(String path, String dirSeparator) {
		return Arrays.asList(path.split(dirSeparator));
	}

	public static ResourcePath fromString(String text) {
		return fromString(text, ResourcePath.DEFAULT_NAMESPACE_SEPARATOR, ResourcePath.DEFAULT_DIR_SEPARATOR);
	}

	@Override
	public String namespace() {
		return this.namespace;
	}

	@Override
	public String path(String dirSeparator) {
		return String.join(dirSeparator, this.path);
	}

	@Override
	public String toString() {
		return this.representation();
	}
}
