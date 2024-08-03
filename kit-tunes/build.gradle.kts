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
    include(project(":kit-tunes-api"))
    include(project(":kitten-heart"))

    include(project(":kitten-star:r1.17.0"))
    include(project(":kitten-star:r1.20.4"))

    include(project(":kitten-square:r1.17.0"))
    include(project(":kitten-square:r1.20.4"))
    include(project(":kitten-square:r1.21.0"))
}

tasks.withType<Jar> {
    from("LICENSE")
}
