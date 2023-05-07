plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.mist.common"
    compileSdk = 33

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    api("androidx.core:core-ktx:1.10.0")
    api("androidx.appcompat:appcompat:1.6.1")
    api("com.google.android.material:material:1.8.0")

    //region Compose
    val composeBom = platform("androidx.compose:compose-bom:2023.03.00")

    api(composeBom)
    api("androidx.compose.ui:ui")
    api("androidx.compose.ui:ui-tooling-preview")
    api("androidx.compose.ui:ui-graphics")
    api("androidx.core:core-ktx:1.10.0")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    api("androidx.activity:activity-compose:1.7.1")
    //endregion

    //region ComposeTest
    testApi("junit:junit:4.13.2")
    androidTestApi("androidx.test.ext:junit:1.1.5")
    androidTestApi("androidx.test.espresso:espresso-core:3.5.1")
    androidTestApi(composeBom)
    androidTestApi("androidx.compose.ui:ui-test-junit4")
    //endregion

    //region ComposeDebug
    debugApi("androidx.compose.ui:ui-tooling")
    debugApi("androidx.compose.ui:ui-test-manifest")
    //endregion

    //region Arrow
    val arrowVersion = "1.1.2"
    val arrowPlatform = platform("io.arrow-kt:arrow-stack:$arrowVersion")

    api(arrowPlatform)
    api("io.arrow-kt:arrow-core")
    api("io.arrow-kt:arrow-optics")
    api("io.arrow-kt:arrow-fx-coroutines")
    api("io.arrow-kt:arrow-fx-stm")
    //endregion

    //region Koin
    val koinAndroidComposeVersion = "3.4.2"
    val koinAndroidVersion = "3.3.3"
    val koinTestVersion = "3.3.2"

    api("io.insert-koin:koin-androidx-compose:$koinAndroidComposeVersion")
    api("io.insert-koin:koin-android:$koinAndroidVersion")
    api("io.insert-koin:koin-test-junit4:$koinTestVersion")
    //endregion

    //region Room
    val roomVersion = "2.5.1"

    api("androidx.legacy:legacy-support-v4:1.0.0")
    kapt("androidx.room:room-compiler:$roomVersion")
    api("androidx.room:room-runtime:$roomVersion")
    api("androidx.room:room-ktx:$roomVersion")
    testApi("androidx.room:room-testing:$roomVersion")
    //endregion

    //region Serialization
    api("app.softwork:kotlinx-uuid-core:0.0.16")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    //endregion

    //region StateEvent
    api("com.github.leonard-palm:compose-state-events:1.2.3")
    //endregion

    //region other
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-RC")
    //endregion

    //region DataStore
    api("androidx.datastore:datastore-preferences:1.0.0")
    //endregion
}