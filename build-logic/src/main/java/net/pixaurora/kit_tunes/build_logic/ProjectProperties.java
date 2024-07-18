package net.pixaurora.kit_tunes.build_logic;

import java.util.Optional;

import org.gradle.api.Project;

public class ProjectProperties {
    private final Project project;

    public ProjectProperties(Project project) {
        this.project = project;
    }

    public Optional<Object> optionalProperty(Property property) {
        return Optional.ofNullable(project.getProperties().get(property.key()));
    }

    public Optional<String> optionalString(Property property) {
        return this.optionalProperty(property).map(String::valueOf);
    }

    public String requireString(Property property) {
        return this.optionalString(property).get();
    }

    public boolean has(Property property) {
        return this.project.hasProperty(property.key());
    }
}
