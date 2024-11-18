import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    kotlin("multiplatform") version "2.0.21"
    id("dev.petuska.npm.publish") version "3.4.3"
}

group = "com.dzikoysk"
version = "1.0.16"

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser()
        generateTypeScriptDefinitions()
        binaries.library()
    }
    jvm {
        withJava()

        compilations {
            java {
                toolchain {
                    languageVersion.set(JavaLanguageVersion.of(11))
                }
            }

            tasks.withType<JavaCompile> {
                options.encoding = "UTF-8"
            }
            tasks.withType<Test> {
                useJUnitPlatform()
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("de.cketti.unicode:kotlin-codepoints-deluxe:0.9.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jsMain by getting
        val jsTest by getting
        val jvmMain by getting
        val jvmTest by getting
    }
}

npmPublish {
    readme.set(file("README.md"))

    packages {
        named("js") {
            packageJson {
                description.set("Hangul processor for Kotlin Multiplatform & JavaScript projects, based on reverse-engineered Branah keyboard algorithm")
                author {
                    name.set("dzikoysk")
                }
                repository {
                    type.set("git")
                    url.set("https://github.com/dzikoysk/khangul")
                }
                bugs {
                    url.set("https://github.com/dzikoysk/khangul")
                }
            }
        }
    }

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
        "allTests",
        "assembleJsPackage",
        "packJsPackage",
        "publishJsPackageToNpmjsRegistry"
    )

    tasks.findByName("test")?.mustRunAfter("clean")
    tasks.findByName("kotlinStoreYarnLock")?.dependsOn("kotlinUpgradeYarnLock")
    tasks.findByName("assembleJsPackage")?.mustRunAfter("allTests")
    tasks.findByName("packJsPackage")?.mustRunAfter("assembleJsPackage")
    tasks.findByName("publishJsPackageToNpmjsRegistry")?.mustRunAfter("packJsPackage")
}


// https://kotlinlang.org/docs/whatsnew18.html#new-settings-for-reporting-that-yarn-lock-has-been-updated
rootProject.plugins.withType(YarnPlugin::class.java) {
    rootProject.the<YarnRootExtension>().yarnLockMismatchReport = YarnLockMismatchReport.WARNING // NONE | FAIL
    rootProject.the<YarnRootExtension>().reportNewYarnLock = false // true
    rootProject.the<YarnRootExtension>().yarnLockAutoReplace = true // true
}