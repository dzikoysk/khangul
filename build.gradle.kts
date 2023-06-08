import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    kotlin("multiplatform") version "1.8.21"
    id("dev.petuska.npm.publish") version "3.3.1"
}

group = "com.dzikoysk"
version = "1.0.5"

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        binaries.library()
        browser()
        generateTypeScriptDefinitions()
    }

    jvm {
        jvmToolchain(11)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
            jvmArgs = listOf("-Dfile.encoding=UTF-8")
        }
    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("de.cketti.unicode:kotlin-codepoints-deluxe:0.6.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}

npmPublish {
    readme.set(file("README.md"))

    registries {
        register("npmjs") {
            uri.set("https://registry.npmjs.org")
            authToken.set(property("npm.token").toString())
        }
    }
}

tasks.register("publishNpm") {
    dependsOn(
        "clean",
        "test",
        "assembleJsPackage",
        "packJsPackage",
        "publishJsPackageToNpmjsRegistry"
    )

    tasks.findByName("test")?.mustRunAfter("clean")
    tasks.findByName("kotlinStoreYarnLock")?.dependsOn("kotlinUpgradeYarnLock")
    tasks.findByName("assembleJsPackage")?.mustRunAfter("test")
    tasks.findByName("packJsPackage")?.mustRunAfter("assembleJsPackage")
    tasks.findByName("publishJsPackageToNpmjsRegistry")?.mustRunAfter("packJsPackage")
}


// https://kotlinlang.org/docs/whatsnew18.html#new-settings-for-reporting-that-yarn-lock-has-been-updated
rootProject.plugins.withType(YarnPlugin::class.java) {
    rootProject.the<YarnRootExtension>().yarnLockMismatchReport = YarnLockMismatchReport.WARNING // NONE | FAIL
    rootProject.the<YarnRootExtension>().reportNewYarnLock = false // true
    rootProject.the<YarnRootExtension>().yarnLockAutoReplace = true // true
}