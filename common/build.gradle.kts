plugins {
    id("com.android.library")
    kotlin("multiplatform")
    //id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

val roomVersion = "2.6.1"

kotlin {
    androidTarget()
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }

        val androidMain by getting {
            dependencies {
                implementation(project(":api"))

                api("androidx.core:core-ktx:1.12.0")
                api("androidx.appcompat:appcompat:1.6.1")
                api("com.google.android.material:material:1.10.0")

                //region Compose
                val composeBom = platform("androidx.compose:compose-bom:2023.03.00")

                api(composeBom)
                api("androidx.compose.ui:ui")
                api("androidx.compose.ui:ui-tooling-preview")
                api("androidx.compose.ui:ui-graphics")
                api("androidx.core:core-ktx:1.12.0")
                api("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
                api("androidx.activity:activity-compose:1.8.0")
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
                val koinAndroidComposeVersion = "3.5.0"
                val koinAndroidVersion = "3.5.0"
                val koinTestVersion = "3.3.2"

                api("io.insert-koin:koin-androidx-compose:$koinAndroidComposeVersion")
                api("io.insert-koin:koin-android:$koinAndroidVersion")
                api("io.insert-koin:koin-test-junit4:$koinTestVersion")
                //endregion

                //region Room
                api("androidx.legacy:legacy-support-v4:1.0.0")
                api("androidx.room:room-runtime:$roomVersion")
                api("androidx.room:room-ktx:$roomVersion")
                //endregion

                //region Serialization
                api("app.softwork:kotlinx-uuid-core:0.0.17")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                //endregion

                //region StateEvent
                api("com.github.leonard-palm:compose-state-events:2.0.3")
                //endregion

                //region other
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                //endregion

                //region DataStore
                api("androidx.datastore:datastore-preferences:1.0.0")
                //endregion

                //region Ktor
                val ktorVersion = "2.3.5"
                api("io.ktor:ktor-client-serialization:$ktorVersion")
                api("io.ktor:ktor-client-android:$ktorVersion")
                api("io.ktor:ktor-client-core:$ktorVersion")
                api("io.ktor:ktor-client-cio:$ktorVersion")
                api("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                api("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                api("io.ktor:ktor-client-auth:$ktorVersion")
                api("io.ktor:ktor-client-logging:$ktorVersion")
                api("io.ktor:ktor-client-okhttp:$ktorVersion")
                //endregion
            }
        }
    }
}

android {
    namespace = "com.mist.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 27
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

dependencies {
    ksp("androidx.room:room-compiler:$roomVersion")
    //add("kspCommonMainMetadata", "androidx.room:room-compiler:$roomVersion")
}

