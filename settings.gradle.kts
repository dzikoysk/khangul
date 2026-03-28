pluginManagement {
    plugins {
        kotlin("multiplatform") version "2.3.20"
        id("dev.petuska.npm.publish") version "3.5.3"
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "khangul-parent"

include("khangul")
include("khangul-site")
