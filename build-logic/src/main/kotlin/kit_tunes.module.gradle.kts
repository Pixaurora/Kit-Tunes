import net.pixaurora.kit_tunes.build_logic.ProjectMetadata

plugins {
    id("kit_tunes.base")
    id("kit_tunes.default_resources")
    id("org.quiltmc.loom")
}

val metadata = extra.get("metadata") as ProjectMetadata

loom {
    mods.create(metadata.modId()) {
        sourceSet(sourceSets.main.get())
    }
}

dependencies {
    modImplementation("org.quiltmc:quilt-loader:0.26.0")

    mappings(loom.officialMojangMappings())
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
}
