plugins {
    id("kit_tunes.java.16")
    id("kit_tunes.submodule")
}

mod {
    intermediaryMappings = "net.fabricmc:intermediary"
    mixin("kitten_star.mixins.json")
}

dependencies {
    implementation(project(":projects:kit-tunes-api"))
    implementation(project(":projects:kitten-heart"))
}
