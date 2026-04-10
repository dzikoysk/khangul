plugins {
    kotlin("multiplatform")
    id("dev.petuska.npm.publish")
}

group = "com.dzikoysk"
version = "1.1.1"

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
                implementation("de.cketti.unicode:kotlin-codepoints-deluxe:0.11.0")
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
    readme.set(rootProject.file("README.md"))

    packages {
        named("js") {
            packageJson {
                description.set("Hangul processor for Kotlin Multiplatform & JavaScript projects, based on reverse-engineered Branah keyboard algorithm")
                license.set("Apache-2.0")
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
    tasks.findByName("assembleJsPackage")?.mustRunAfter("allTests")
    tasks.findByName("packJsPackage")?.mustRunAfter("assembleJsPackage")
    tasks.findByName("publishJsPackageToNpmjsRegistry")?.mustRunAfter("packJsPackage")
}
