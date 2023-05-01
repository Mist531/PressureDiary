package com.mist.pressurediary.navigation

import androidx.annotation.StringRes
import com.mist.pressurediary.R

sealed class Screen(
    open val route: String,
    @StringRes open val id: Int
) {
    
    object Main : Screen(
        route = "main",
        id = R.string.screen_main
    )

    object CreatePressure : Screen(
        route = "create_entry",
        id = R.string.screen_create_entry
    )

    object UpdatePressure : Screen(
        route = "update_entry",
        id = R.string.screen_update_entry
    )

    object History : Screen(
        route = "history",
        id = R.string.screen_history
    )

    object Settings : Screen(
        route = "settings",
        id = R.string.screen_settings
    )

    companion object {
        val screensNeedShowName: List<Screen> = listOf(
            CreatePressure,
            UpdatePressure,
            History,
            Settings
        )
    }
}