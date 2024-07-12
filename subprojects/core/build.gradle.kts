plugins {
    id("kit_tunes.java.08")
    id("kit_tunes.base")
}

dependencies {
    implementation(project(":subprojects:api"))

    implementation(libs.annotations)
    implementation(libs.quilt.loader)
    implementation(libs.gson)
    implementation(libs.slf4j)
}
