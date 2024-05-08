package net.pixaurora.kit_tunes.api.resource;

import java.util.List;

public class ResourcePath {
	public static final String DIR_SEPARATOR = ".";
	public static final String PARSING_DIR_SEPARATOR = "\\.";

	private final List<String> path;

	public ResourcePath(String... path) {
		this.path = List.of(path);
	}

	public static ResourcePath fromString(String text, String dirSeparator) {
		return new ResourcePath(text.split(dirSeparator));
	}

	public static ResourcePath fromString(String string) {
		return fromString(string, PARSING_DIR_SEPARATOR);
	}

	public String path(String dirSeparator) {
		return String.join(dirSeparator, path);
	}

	public String toString(String dirSeparator) {
		return this.path(dirSeparator);
	}

	@Override
	public int hashCode() {
		return this.path.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof ResourcePath) {
			return ((ResourcePath) other).path.equals(this.path);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return this.toString(DIR_SEPARATOR);
	}
}
