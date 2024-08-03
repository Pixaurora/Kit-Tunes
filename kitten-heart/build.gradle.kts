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

    dependencies {
        required("quilt_loader").versionAbove(libs.versions.quilt.loader.get())


        required("kit_tunes_api")
        required("kitten_square")
        required("kitten_star")
    }
}

dependencies {
    implementation(project(":kit-tunes-api"))

    implementation(libs.annotations)
    implementation(libs.quilt.loader)
    implementation(libs.gson)
    implementation(libs.slf4j)
}
