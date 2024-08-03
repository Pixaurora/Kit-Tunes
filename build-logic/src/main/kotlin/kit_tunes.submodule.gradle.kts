import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModResourcesExtension

plugins {
    id("kit_tunes.module")
}

val minecraft_version_min = project.property("minecraft_version_min") as String
val minecraft_version_max = project.property("minecraft_version_max") as String

configure<ModResourcesExtension>{
    metadata {
        library()
        parentModId = "kit_tunes"
    }

    dependencies {
        required("minecraft").version(minecraft_version_min, minecraft_version_max)
    }
}
