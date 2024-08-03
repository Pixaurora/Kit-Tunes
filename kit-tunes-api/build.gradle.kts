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
        required("kitten_heart")
    }
}
