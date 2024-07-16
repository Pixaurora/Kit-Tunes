package net.pixaurora.kit_tunes.impl.resource;

import java.nio.file.Path;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public class ResourcePathUtils {
    public static Optional<ResourcePath> stripPrefix(String prefix, ResourcePath path) {
        return stripPrefix(prefix, path.path()).map(pathPart -> new ResourcePathImpl(path.namespace(), pathPart));
    }

    public static Optional<ResourcePath> stripSuffix(String suffix, ResourcePath path) {
        return stripSuffix(suffix, path.path()).map(pathPart -> new ResourcePathImpl(path.namespace(), pathPart));
    }

    public static Optional<ResourcePath> stripSuffixAndPrefix(String prefix, String suffix, ResourcePath path) {
        return stripPrefix(prefix, path.path()).flatMap(pathPart -> stripSuffix(suffix, pathPart))
                .map(pathPart -> new ResourcePathImpl(path.namespace(), pathPart));
    }

    public static Optional<ResourcePath> metadataPathToResource(Path metadataPath) {
        String normalizedPath = metadataPath.normalize().toString();

        return stripPrefix("/assets/", normalizedPath).flatMap(path -> stripSuffix(".json", path))
                .map(path -> ResourcePathImpl.fromString(path, "/music_metadata/", "/"));
    }

    private static Optional<String> stripPrefix(String prefix, String text) {
        if (text.startsWith(prefix)) {
            return Optional.of(text.substring(prefix.length()));
        } else {
            return Optional.empty();
        }
    }

    private static Optional<String> stripSuffix(String suffix, String text) {
        if (text.endsWith(suffix)) {
            return Optional.of(text.substring(0, text.length() - suffix.length()));
        } else {
            return Optional.empty();
        }
    }

}
