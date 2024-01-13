package com.mist.mobile_app.navigation

sealed class Screens(
    val route: String,
    val screenName: String = ""
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
}