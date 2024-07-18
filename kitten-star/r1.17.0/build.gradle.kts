plugins {
    id("kit_tunes.java.17")
    id("kit_tunes.module")
}

dependencies {
    implementation(project(":kit-tunes-api"))
    implementation(project(":kitten-heart"))
}
