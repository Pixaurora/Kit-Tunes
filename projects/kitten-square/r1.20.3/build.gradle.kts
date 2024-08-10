plugins {
    id("kit_tunes.java.17")
    id("kit_tunes.submodule")
}

val modmenu_version = project.property("modmenu_version") as String

mod {
    dependencies {
        required("fabric-resource-loader-v0")
        optional("modmenu").versionAbove(modmenu_version)
    }

    entrypoint("modmenu", "net.pixaurora.kitten_square.impl.compat.ModMenuIntegration")

    intermediaryMappings = "net.fabricmc:intermediary"
    mixin("kitten_square.mixins.json")
}

repositories {
    maven {
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/")
    }
}

dependencies {
    implementation(project(":projects:kit-tunes-api"))
    implementation(project(":projects:kitten-heart"))

    modImplementation(libs.qsl.resource.loader)

    modImplementation("com.terraformersmc:modmenu:${modmenu_version}")
}
