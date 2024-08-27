package net.pixaurora.kitten_heart.impl.resource;

import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonParseException;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.config.DualSerializer;
import net.pixaurora.kitten_heart.impl.config.DualSerializerFromString;

public class ResourcePathImpl implements ResourcePath {
    public static final DualSerializer<ResourcePath> SERIALIZER = new DualSerializerFromString<>(
            ResourcePath::representation, ResourcePathImpl::fromString);

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

    public static ResourcePath fromString(String text, String namespaceSeparator, String dirSeparator)
            throws JsonParseException {
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

    @Override
    public int hashCode() {
        return 31 * this.namespace.hashCode() + this.path.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof ResourcePathImpl && this.hashCode() == other.hashCode();
    }
}
