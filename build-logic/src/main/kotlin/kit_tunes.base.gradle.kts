import net.pixaurora.kit_tunes.build_logic.ProjectMetadata

plugins {
    id("base")
}

val metadata = ProjectMetadata(project)
extra.set("metadata", metadata)

version = metadata.version()

base {
	archivesName = metadata.archiveName()
}

repositories {
	mavenCentral()
	maven {
		name = "Quilt"
		url = uri("https://maven.quiltmc.org/repository/release")
	}
}

tasks.withType<ProcessResources> {
	inputs.property("version", metadata.version())

	filesMatching("quilt.mod.json") {
		expand(inputs.properties)
	}
}
