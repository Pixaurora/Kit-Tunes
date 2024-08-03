import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.ModResourcesPlugin
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModResourcesExtension

plugins {
    id("kit_tunes.base")
}

apply<ModResourcesPlugin>()

configure<ModResourcesExtension>{
    modIdFromProperties()
    version = workaroundProperty("mod_version")

    metadata {
        name = nameFromModId()
        description = workaroundProperty("description");

        modIcon = iconFromModId()
    }
}
