plugins {
    id("kit_tunes.java.16")
    id("kit_tunes.module")
}

repositories {
    maven {
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/")
    }
}

dependencies {
    implementation(project(":kit-tunes-api"))
    implementation(project(":kitten-heart"))

    modImplementation(libs.qsl.resource.loader)

    modImplementation("com.terraformersmc:modmenu:${project.property("modmenu_version")}")
    modImplementation(fabricApi.module("fabric-resource-loader-v0", project.property("fabric_api_version").toString()))
}
