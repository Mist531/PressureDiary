package com.mist.pressurediary.navigation

sealed class Screen(open val route: String) {
    object Main : Screen(route = "main")

    object AddPressure : Screen(route = "add_pressure")

    object History : Screen(route = "history")

    object Settings : Screen(route = "settings")
}