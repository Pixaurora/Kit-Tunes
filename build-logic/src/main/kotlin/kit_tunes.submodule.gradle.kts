import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModResourcesExtension

plugins {
    id("kit_tunes.module")
}

val minecraftVersionMin = project.property("minecraft_version_min") as String
val minecraftVersionMax = project.property("minecraft_version_max") as String

configure<ModResourcesExtension>{
    metadata {
        library()
        parentModId = "kit_tunes"
    }

    dependencies {
        required("minecraft").version(minecraftVersionMin, minecraftVersionMax)
    }
}
