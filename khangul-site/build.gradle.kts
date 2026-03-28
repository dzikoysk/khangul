plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        jsMain.dependencies {
            implementation(project(":khangul"))
        }
    }
}

tasks.register<Sync>("deployToDocs") {
    dependsOn("jsBrowserDistribution")
    from("build/dist/js/productionExecutable") {
        exclude("*.map")
    }
    into("${rootProject.projectDir}/docs")
}
