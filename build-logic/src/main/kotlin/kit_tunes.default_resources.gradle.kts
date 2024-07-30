import net.pixaurora.kit_tunes.build_logic.ProjectMetadata
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.ModResourcesPlugin
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.ModIcon
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModResourcesExtension

plugins {
    id("kit_tunes.base")
}

apply<ModResourcesPlugin>()

val metadata = extra.get("metadata") as ProjectMetadata

configure<ModResourcesExtension>{
    modIcon = ModIcon.fromModId(metadata.modId())
}
