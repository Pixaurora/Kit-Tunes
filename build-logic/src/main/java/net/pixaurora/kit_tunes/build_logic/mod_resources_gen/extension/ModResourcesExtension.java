package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension;

import java.nio.file.Path;
import java.util.Optional;

import org.gradle.api.Action;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Nested;

public abstract class ModResourcesExtension {
    private Optional<Path> mixinFile = Optional.empty();

    @Input
    public abstract Property<String> getId();

    @Input
    public abstract Property<String> getVersion();

    @Nested
    public abstract ModMetadata getMetadata();

    @Nested
    public abstract ModDependencies getDependencies();

    @Input
    public abstract Property<String> getIntermediaryMappings();

    public Optional<Path> mixinFile() {
        return this.mixinFile;
    }

    public void mixin(String path) {
        var mixinFile = Path.of(path);
        this.mixinFile = Optional.of(mixinFile);
    }

    public void metadata(Action<? super ModMetadata> configuration) {
        configuration.execute(this.getMetadata());
    }

    public void dependencies(Action<? super ModDependencies> configuration) {
        configuration.execute(this.getDependencies());
    }
}
