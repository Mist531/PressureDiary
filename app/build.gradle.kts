plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.mist.pressurediary"
    compileSdk = 33

    defaultConfig {
        val versionMajor = 1
        val versionMinor = 1

        applicationId = "com.mist.pressurediary"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "${versionMajor}.${versionMinor}.${versionCode}"

        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    val composeBom = platform("androidx.compose:compose-bom:2023.03.00")

    //region Horologist
    val horologistVersion = "0.4.4"

    implementation("com.google.android.horologist:horologist-composables:$horologistVersion")
    implementation("com.google.android.horologist:horologist-compose-layout:$horologistVersion")
    implementation("com.google.android.horologist:horologist-media-ui:$horologistVersion")
    //endregion

    //region Koin
    val koinAndroidComposeVersion = "3.4.2"
    val koinAndroidVersion= "3.3.3"
    val koinTestVersion = "3.3.2"

    implementation("io.insert-koin:koin-androidx-compose:$koinAndroidComposeVersion")
    implementation("io.insert-koin:koin-android:$koinAndroidVersion")
    testImplementation("io.insert-koin:koin-test-junit4:$koinTestVersion")
    //endregion

    //region Room
    val roomVersion = "2.5.1"
    
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    //endregion

    //region ComposeForWearOs
    val wearComposeVersion = "1.2.0-alpha09"

    implementation("androidx.wear.compose:compose-material:$wearComposeVersion")
    implementation("androidx.wear.compose:compose-foundation:$wearComposeVersion")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    //endregion

    //region Compose
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.1")
    //endregion

    //region ComposeTest
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    //endregion

    //region ComposeDebug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //endregion

    //region Arrow
    val arrowVersion = "1.1.2"
    val arrowPlatform = platform("io.arrow-kt:arrow-stack:$arrowVersion")

    implementation(arrowPlatform)
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-optics")
    implementation("io.arrow-kt:arrow-fx-coroutines")
    implementation("io.arrow-kt:arrow-fx-stm")
    //endregion

    //region Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("app.softwork:kotlinx-uuid-core:0.0.16")
    //endregion

    //region Other
    implementation("com.google.android.gms:play-services-wearable:18.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    //endregion
}