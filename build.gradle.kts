plugins {
    id("kit_tunes.java.17")
    id("kit_tunes.module")
    id("kit_tunes.default_resources")
}

mod {
    dependencies {
        required("quilt_loader").versionAbove(libs.versions.quilt.loader.get())
        required("kitten_heart")
    }
}

dependencies {
    include(project(":projects:kit-tunes-api"))
    include(project(":projects:kitten-heart"))

    include(project(":projects:kitten-sounds:r1.17.0"))
    include(project(":projects:kitten-sounds:r1.20.3"))

    include(project(":projects:kitten-square:r1.17.0"))
    include(project(":projects:kitten-square:r1.20.0"))
    include(project(":projects:kitten-square:r1.20.3"))
    include(project(":projects:kitten-square:r1.21.0"))
}

tasks.withType<Jar> {
    from("LICENSE")
}
