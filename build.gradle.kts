import me.modmuss50.mpp.ReleaseType
import net.fabricmc.loom.task.RemapJarTask
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    id("kit_tunes.java.17")
    id("kit_tunes.module")
    id("kit_tunes.default_resources")
    alias(libs.plugins.mod.publish.plugin)
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
    include(project(":projects:catculator"))

    include(project(":projects:kitten-sounds:r1.17.0"))
    include(project(":projects:kitten-sounds:r1.20.3"))

    include(project(":projects:kitten-square:r1.17.0"))
    include(project(":projects:kitten-square:r1.19.0"))
    include(project(":projects:kitten-square:r1.19.3"))
    include(project(":projects:kitten-square:r1.19.4"))
    include(project(":projects:kitten-square:r1.20.0"))
    include(project(":projects:kitten-square:r1.20.3"))
    include(project(":projects:kitten-square:r1.21.0"))
    include(project(":projects:kitten-square:r1.21.2"))
}

tasks.withType<Jar> {
    from("LICENSE")
}

val modVersion = project.property("mod_version").toString()
val updateTitle = project.property("update_title").toString().uppercaseFirstChar()

fun getVersionType(): ReleaseType {
    return if (modVersion.startsWith("0.") || modVersion.contains("-alpha.")) {
        ReleaseType.ALPHA;
    } else if (modVersion.contains("-")) {
        ReleaseType.BETA;
    } else {
        ReleaseType.STABLE;
    }
}

publishMods {
    version = modVersion
    displayName = "Kit Tunes $updateTitle $modVersion"

    type = getVersionType()
    modLoaders.add("quilt")

    file = tasks.withType<RemapJarTask>()["remapJar"].archiveFile
    changelog = file(rootDir.toPath().resolve("src/main/resources/changelog/${modVersion}.txt")).readText()

    github {
        accessToken = providers.environmentVariable("GITHUB_SECRET")

        commitish = "meow"
        tagName = "v${modVersion}"
        repository = "Pixaurora/Kit-Tunes"
    }

    modrinth {
        accessToken = providers.environmentVariable("MODRINTH_SECRET")
        projectId = "AVOKl7hB"

        minecraftVersionRange {
            start = project.property("publish_version_min").toString()
            end = project.property("publish_version_max").toString()
        }

        optional("fabric-api", "qsl", "modmenu")
    }
}
