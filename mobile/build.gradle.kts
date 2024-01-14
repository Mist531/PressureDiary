plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.20"
}

android {
    namespace = "com.mist.mobile_app"
    compileSdk = 34

    defaultConfig {
        val versionMajor = 1
        val versionMinor = 1

        applicationId = "com.mist.mobile_app"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "${versionMajor}.${versionMinor}.(${versionCode})"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        resourceConfigurations += listOf("ru", "en")
    }

    buildTypes {
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
        kotlinCompilerExtensionVersion = "1.5.5"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":common"))

    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
}