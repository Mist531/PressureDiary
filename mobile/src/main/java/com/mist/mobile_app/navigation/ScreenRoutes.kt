package com.mist.mobile_app.navigation

import androidx.annotation.DrawableRes
import com.mist.mobile_app.R

sealed class Screens(
    open val route: String,
    open val screenName: String = ""
) {
    data object SplashScreen : Screens(
        route = "splash_screen",
    )

    data object Auth : Screens(
        route = "auth",
    ) {
        data object Registration : Screens(
            route = "registration"
        )

        data object Login : Screens(
            route = "login"
        )
    }

    data object MainScreen : Screens(
        route = "main_screen",
    )

    open class HomeBottomNavItem(
        @DrawableRes val icon: Int,
        override val route: String,
        override val screenName: String,
    ) : Screens(route = MainScreen.route) {
        companion object {
            val items: List<HomeBottomNavItem> = listOf(
                History,
                Statistics,
                Settings,
            )
        }

         object History : HomeBottomNavItem(
            icon = com.mist.common.R.drawable.ic_history,
            route = "history",
            screenName = "История"
        )

         object Statistics : HomeBottomNavItem(
            icon = R.drawable.ic_statistics,
            route = "statistics",
            screenName = "Статистика"
        )

         object Settings : HomeBottomNavItem(
            icon = com.mist.common.R.drawable.ic_settings,
            route = "settings",
            screenName = "Настройки"
        )
    }
}