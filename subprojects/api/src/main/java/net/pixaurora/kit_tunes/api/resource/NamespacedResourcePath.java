package net.pixaurora.kit_tunes.api.resource;

import com.google.gson.JsonParseException;

public class NamespacedResourcePath {
	public static final String NAMESPACE_SEPARATOR = ":";
	public static final String DIR_SEPARATOR = "/";

	private final String namespace;
	private final ResourcePath path;

	public NamespacedResourcePath(String namespace, ResourcePath path) {
		this.namespace = namespace;
		this.path = path;
	}

	public static NamespacedResourcePath fromString(String text) throws JsonParseException {
		String[] parts = text.split(NAMESPACE_SEPARATOR, 2);

		if (parts.length != 2) {
			throw new JsonParseException("Namespaced Resource Paths require a " + NAMESPACE_SEPARATOR + " to be present!");
		}

		return new NamespacedResourcePath(parts[0], ResourcePath.fromString(parts[1], DIR_SEPARATOR));
	}

	public String namespace() {
		return this.namespace;
	}

	public String path() {
		return this.path.toString(DIR_SEPARATOR);
	}

	public String toString() {
		return this.namespace + NAMESPACE_SEPARATOR + this.path();
	}
}
