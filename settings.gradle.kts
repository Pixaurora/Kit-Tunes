pluginManagement {
    repositories {
        maven {
            name = "Quilt"
            url = uri("https://maven.quiltmc.org/repository/release")
        }
        // Currently needed for Intermediary and other temporary dependencies
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

includeBuild("build-logic")

include("subprojects:api")
include("subprojects:core")

include("subprojects:sound_events:r1.20.4")

include("subprojects:ui:r1.20.4")
include("subprojects:ui:r1.21.0")