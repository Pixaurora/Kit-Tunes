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

include("projects:kit-tunes-api")
include("projects:kitten-heart")

include("projects:kitten-sounds:r1.17.0")
include("projects:kitten-sounds:r1.20.3")

include("projects:kitten-square:r1.17.0")
include("projects:kitten-square:r1.20.3")
include("projects:kitten-square:r1.21.0")
