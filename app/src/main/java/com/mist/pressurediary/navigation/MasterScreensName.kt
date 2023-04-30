package com.mist.pressurediary.navigation

sealed class Screen(open val route: String) {
    object Main : Screen(route = "Main")

    object CreatePressure : Screen(route = "Create Entry")

    object History : Screen(route = "History")

    object Settings : Screen(route = "Settings")
}