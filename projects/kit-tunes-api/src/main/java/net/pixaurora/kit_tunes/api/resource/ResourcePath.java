package net.pixaurora.kit_tunes.api.resource;

public interface ResourcePath {
    public static final String DEFAULT_NAMESPACE_SEPARATOR = ":";
    public static final String DEFAULT_DIR_SEPARATOR = "/";

    public String namespace();

    public String path(String dirSeparator);

    public default String path() {
        return this.path(DEFAULT_DIR_SEPARATOR);
    }

    public default String representation(String namespaceSeparator, String dirSeparator) {
        return namespace() + namespaceSeparator + path(dirSeparator);
    }

    public default String representation() {
        return this.representation(DEFAULT_NAMESPACE_SEPARATOR, DEFAULT_DIR_SEPARATOR);
    }
}
