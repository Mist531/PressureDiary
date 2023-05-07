plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.mist.wear_os"
    compileSdk = 33

    defaultConfig {
        val versionMajor = 1
        val versionMinor = 1

        applicationId = "com.mist.wear_os"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "${versionMajor}.${versionMinor}.${versionCode}"

        vectorDrawables {
            useSupportLibrary = true
        }

        resourceConfigurations += listOf("ru","en")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":common"))

    //region Horologist
    val horologistVersion = "0.4.4"

    implementation("com.google.android.horologist:horologist-composables:$horologistVersion")
    implementation("com.google.android.horologist:horologist-compose-layout:$horologistVersion")
    implementation("com.google.android.horologist:horologist-media-ui:$horologistVersion")
    //endregion

    //region ComposeForWearOs
    val wearComposeVersion = "1.2.0-alpha09"

    implementation("androidx.wear.compose:compose-material:$wearComposeVersion")
    implementation("androidx.wear.compose:compose-foundation:$wearComposeVersion")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("androidx.wear.compose:compose-navigation:$wearComposeVersion")
    //endregion

    //region Other
    implementation("com.google.android.gms:play-services-wearable:18.0.0")
    //endregion
}