import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget()
    jvm()

    sourceSets {
        val commonMain by getting

        val androidMain by getting {
            dependencies {
                api(project(":server:api"))

                api(libs.kotlinx.uuid.core)

                api(libs.core.ktx)
                api(libs.appcompat)
                api(libs.material)

                //region Compose
                api(project.dependencies.platform(libs.androidx.compose.bom))
                api(libs.ui)
                api(libs.ui.tooling)
                api(libs.ui.tooling.preview)
                api(libs.ui.graphics)
                api(libs.core.ktx)
                api(libs.lifecycle.runtime.ktx)
                api(libs.activity.compose)
                //endregion

                //region Arrow
                api(project.dependencies.platform(libs.arrow.stack))
                api(libs.arrow.kt.arrow.core)
                api(libs.arrow.kt.arrow.optics)
                api(libs.arrow.kt.arrow.fx.coroutines)
                api(libs.arrow.kt.arrow.fx.stm)
                //endregion

                //region Koin
                api(libs.koin.androidx.compose)
                api(libs.koin.android)
                api(libs.koin.test.junit4)
                //endregion

                // UI Tests
                api(libs.ui.test.manifest)
                api(libs.ui.test.junit4)
                api(libs.junit)
                api(libs.espresso.core)
                //endregion

                //region Room
                api(libs.legacy.support.v4)
                api(libs.room.runtime)
                api(libs.room.ktx)
                //endregion

                //region StateEvent
                api(libs.compose.state.events)
                //endregion

                //region other
                api(libs.kotlinx.coroutines.core)
                //endregion

                //region DataStore
                api(libs.datastore.preferences)
                //endregion

                //region Ktor
                api(libs.ktor.client.okhttp)
                api(libs.ktor.client.serialization)
                api(libs.ktor.client.android)
                api(libs.ktor.client.core)
                api(libs.ktor.client.cio)
                api(libs.ktor.client.content.negotiation)
                api(libs.ktor.serialization.kotlinx.json)
                api(libs.ktor.client.auth)
                api(libs.ktor.client.logging)
                //endregion

                api(libs.lifecycle.runtime.compose)
            }
        }
    }
}

android {
    namespace = "io.pressurediary.android.common"
    compileSdk = libs.versions.androidSdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidSdk.min.get().toInt()
    }
}

composeCompiler {
    featureFlags = setOf(
        ComposeFeatureFlag.OptimizeNonSkippingGroups,
    )
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(
            libs.versions.javaVersion.get()
        )
    }
}

dependencies {
    ksp(libs.room.compiler)
}

