plugins {
	id("kit_tunes.java.17")
	id("kit_tunes.module")
}

dependencies {
	include(project(":api"))
	include(project(":heart"))

	include(project(":sound_events:r1.20.4"))

	include(project(":ui:r1.20.4"))
	include(project(":ui:r1.21.0"))
}

tasks.withType<Jar> {
    from("LICENSE")
}
