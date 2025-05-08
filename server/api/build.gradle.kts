plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget()
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.serialization.json)
            }
        }

        val androidMain by getting
        val jvmMain by getting
    }
}

android {
    namespace = "io.pressurediary.server.api"
    compileSdk = libs.versions.androidSdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidSdk.min.get().toInt()
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(
            libs.versions.javaVersion.get()
        )
    }
}