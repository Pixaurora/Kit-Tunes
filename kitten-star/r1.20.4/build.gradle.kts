plugins {
    id("kit_tunes.java.17")
    id("kit_tunes.submodule")
}

dependencies {
    implementation(project(":kit-tunes-api"))
    implementation(project(":kitten-heart"))
}

mod {
    intermediaryMappings = "net.fabricmc:intermediary"
    mixin("kitten_star.mixins.json")
}
