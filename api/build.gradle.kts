plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget()
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                //region Serialization
                api("app.softwork:kotlinx-uuid-core:0.0.17")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                //endregion
            }
        }

        val androidMain by getting {
            dependencies {

            }
        }
        val jvmMain by getting {
            dependencies {

            }
        }
    }
}

android {
    namespace = "com.example.api"
    compileSdk = 34

    defaultConfig {
        minSdk = 27
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}