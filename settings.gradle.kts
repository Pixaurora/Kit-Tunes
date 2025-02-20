pluginManagement {
    repositories {
        maven {
            name = "Quilt"
            url = uri("https://maven.quiltmc.org/repository/release")
        }
        maven {
            name = "Ornithe Releases"
            url = uri("https://maven.ornithemc.net/releases")
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
include("projects:catculator")

include("projects:kitten-sounds:b1.7.3")
include("projects:kitten-sounds:r1.17.0")
include("projects:kitten-sounds:r1.20.3")

include("projects:kitten-square:b1.7.3")
include("projects:kitten-square:r1.17.0")
include("projects:kitten-square:r1.19.0")
include("projects:kitten-square:r1.19.3")
include("projects:kitten-square:r1.19.4")
include("projects:kitten-square:r1.20.0")
include("projects:kitten-square:r1.20.2")
include("projects:kitten-square:r1.20.3")
include("projects:kitten-square:r1.21.0")
include("projects:kitten-square:r1.21.2")
