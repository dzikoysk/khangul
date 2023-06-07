plugins {
    kotlin("js") version "1.8.21"
    id("dev.petuska.npm.publish") version "3.3.1"
}

group = "com.dzikoysk"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
}

npmPublish {
    registries {
        register("npmjs") {
            uri.set("https://registry.npmjs.org")
            authToken.set(System.getProperty("npm.token") ?: "")
        }
    }
}