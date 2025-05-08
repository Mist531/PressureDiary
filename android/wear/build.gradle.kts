import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "io.pressurediary.android.wear"
    compileSdk = libs.versions.androidSdk.compile.get().toInt()

    defaultConfig {
        applicationId = "io.pressurediary.android.wear"
        minSdk = libs.versions.androidSdk.min.get().toInt()
        targetSdk = libs.versions.androidSdk.target.get().toInt()
        versionCode = 1
        versionName = "0.0.1"

        vectorDrawables {
            useSupportLibrary = true
        }

        resourceConfigurations += listOf("ru", "en")
    }

    buildTypes {
        debug {
            isCrunchPngs = false
            isShrinkResources = false
            isMinifyEnabled = false
        }
        release {
            isCrunchPngs = true
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(project(":android:common"))
    implementation(project(":server:api"))

    //region Horologist
    implementation(libs.horologist.composables)
    implementation(libs.horologist.compose.layout)
    implementation(libs.horologist.media.ui)
    //endregion

    //region ComposeForWearOs
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.recyclerview.selection)
    implementation(libs.androidx.compose.navigation)
    //endregion

    //region Other
    implementation(libs.play.services.wearable)
    //endregion
}