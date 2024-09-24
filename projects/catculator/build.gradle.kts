import java.io.ByteArrayOutputStream

plugins {
    id("kit_tunes.java.08")
    id("kit_tunes.base")
    id("kit_tunes.default_resources")
}

mod {
    metadata {
        library()
        parentModId = "kit_tunes"
    }
}

dependencies {
    implementation(libs.annotations)
    implementation(libs.quilt.loader)
}

tasks.register("buildDevNatives") {
    inputs.file(file("Cargo.toml"))
    inputs.file(file("Cargo.lock"))

    inputs.dir(file("src/main/rust"))
    outputs.dir(file("target/debug"))

    doLast {
        val stream = ByteArrayOutputStream()

        exec {
            errorOutput = stream
            standardOutput = stream

            commandLine("cargo", "build")
        }

        logger.info(stream.toString())
    }
}
