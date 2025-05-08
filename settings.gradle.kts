pluginManagement {
    repositories {
        jcenter()
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        jcenter()
        maven("https://jitpack.io")
        google()
        mavenCentral()
    }
}

rootProject.name = "PressureDiary"

/**
 * Name for packages
 * io.pressurediary.android -> {
 *   io.pressurediary.android.common
 *   io.pressurediary.android.mobile
 *   io.pressurediary.android.wear
 * }
 *
 * io.pressurediary.swift -> {
 *   io.pressurediary.swift.common
 *   io.pressurediary.swift.ios
 *   io.pressurediary.swift.watch
 * }
 *
 * io.pressurediary.common
 *
 * io.pressurediary.server -> {
 *   io.pressurediary.server.api
 *   io.pressurediary.server.backend
 * }
 *
 * io.pressurediary.desktop -> {
 *   io.pressurediary.desktop.common
 *   io.pressurediary.desktop.mac
 *   io.pressurediary.desktop.windows
 * }
 */

//region Android
include(":android:wear")
include(":android:common")
include(":android:mobile")
//endregion

//region Network
include(":server:backend")
include(":server:api")
//endregion
