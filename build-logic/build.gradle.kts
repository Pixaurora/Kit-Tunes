plugins {
    `kotlin-dsl`
}

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

dependencies {
    implementation(libs.quilt.loom)
}

kotlin {
    jvmToolchain(21)
}
