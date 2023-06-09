package com.mist.wear_os.navigation

import androidx.annotation.StringRes
import com.mist.wear_os.R

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
    ){
        object Theme : Screen(
            route = "theme",
            id = R.string.screen_theme
        )
    }

    companion object {
        val screensNeedShowName: List<Screen> = listOf(
            CreatePressure,
            UpdatePressure,
            History,
            Settings,
            Settings.Theme
        )
    }
}