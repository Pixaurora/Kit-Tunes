import org.gradle.accessors.dm.LibrariesForLibs
import net.pixaurora.kit_tunes.build_logic.ProjectMetadata

plugins {
    id("kit_tunes.base")
    id("kit_tunes.default_resources")
    id("org.quiltmc.loom")
}

val libs = the<LibrariesForLibs>()
val metadata = extra.get("metadata") as ProjectMetadata

loom {
    mods.create(metadata.modId()) {
        sourceSet(sourceSets.main.get())
    }

    mixin {
        useLegacyMixinAp.set(false) // So we can mixin to libraries
    }
}

dependencies {
    modImplementation(libs.quilt.loader)

    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
}
