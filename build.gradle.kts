plugins {
	id("kit_tunes.java.17")
	id("kit_tunes.module")
}

dependencies {
	include(project(":subprojects:api"))
	include(project(":subprojects:core"))

	include(project(":subprojects:sound_events:r1.20.4"))

	include(project(":subprojects:ui:r1.20.4"))
	include(project(":subprojects:ui:r1.21.0"))
}

tasks.withType<Jar> {
    from("LICENSE")
}
