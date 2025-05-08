package io.pressurediary.android.mobile.navigation

import androidx.annotation.DrawableRes
import io.pressurediary.android.mobile.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(
    @SerialName("homeScreenName")
    open val screenName: String = ""
) {
    @Serializable
    data object SplashScreen : Screens()

    @Serializable
    data object Auth : Screens() {
        @Serializable
        data object Registration : Screens()

        @Serializable
        data object Login : Screens()
    }

    @Serializable
    data object MainScreen : Screens()

    @Serializable
    open class HomeBottomNavItem(
        @DrawableRes val icon: Int,
        override val screenName: String,
    ) : Screens() {
        companion object {
            val items: List<HomeBottomNavItem> = listOf(
                History,
                Statistics,
                Settings,
            )
        }

        @Serializable
        object History : HomeBottomNavItem(
            icon = io.pressurediary.android.common.R.drawable.ic_history,
            screenName = "История"
        )

        @Serializable
        object Statistics : HomeBottomNavItem(
            icon = R.drawable.ic_statistics,
            screenName = "Статистика"
        )

        @Serializable
        object Settings : HomeBottomNavItem(
            icon = io.pressurediary.android.common.R.drawable.ic_settings,
            screenName = "Настройки"
        )
    }
}