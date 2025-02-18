package net.pixaurora.kitten_heart.impl.resource;

import java.nio.file.Path;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public class ResourcePathUtils {
    public static Optional<ResourcePath> stripPrefix(String prefix, ResourcePath path) {
        Optional<String> strippedPath = stripPrefix(prefix, path.path());

        if (!strippedPath.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(new ResourcePathImpl(path.namespace(), strippedPath.get()));
    }

    public static Optional<ResourcePath> stripSuffix(String suffix, ResourcePath path) {
        Optional<String> strippedPath = stripSuffix(suffix, path.path());

        if (!strippedPath.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(new ResourcePathImpl(path.namespace(), strippedPath.get()));
    }

    public static Optional<ResourcePath> stripSuffixAndPrefix(String prefix, String suffix, ResourcePath path) {
        Optional<String> strippedPrefixPath = stripPrefix(prefix, path.path());

        if (!strippedPrefixPath.isPresent()) {
            return Optional.empty();
        }

        Optional<String> strippedPath = stripSuffix(suffix, strippedPrefixPath.get());

        if (!strippedPath.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(new ResourcePathImpl(path.namespace(), strippedPath.get()));
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
