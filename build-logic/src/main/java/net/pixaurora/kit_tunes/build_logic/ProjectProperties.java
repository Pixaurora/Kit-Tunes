package net.pixaurora.kit_tunes.build_logic;

import java.util.Optional;

import org.gradle.api.Project;

public class ProjectProperties {
    private final Project project;

    public ProjectProperties(Project project) {
        this.project = project;
    }

    public Optional<Object> optionalProperty(String property) {
        Optional<Project> parent = Optional.ofNullable(this.project.getParent());

        // This is a work-around for https://github.com/gradle/gradle/issues/29600
        if (project.getName().charAt(1) == '1' && parent.isPresent()) {
            Optional<Object> parentProperty = Optional.ofNullable(parent.get().getProperties().get(property));

            if (parentProperty.isPresent()) {
                return parentProperty;
            }
        }

        return Optional.ofNullable(project.getProperties().get(property));
    }

    public Optional<String> optionalString(String property) {
        return this.optionalProperty(property).map(String::valueOf);
    }

    public String requireString(String property) {
        return this.optionalString(property).get();
    }

    public boolean has(String property) {
        return this.project.hasProperty(property);
    }
}
