import net.pixaurora.kit_tunes.build_logic.ProjectMetadata
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.ModResourcesPlugin
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModResourcesExtension

plugins {
    id("kit_tunes.base")
}

apply<ModResourcesPlugin>()

val metadata = extra.get("metadata") as ProjectMetadata

val modId = metadata.modId()

configure<ModResourcesExtension>{
    metadata {
        modIcon = iconFromModId(modId)
    }
}
