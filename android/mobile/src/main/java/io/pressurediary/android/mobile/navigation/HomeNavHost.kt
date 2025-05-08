package io.pressurediary.android.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import io.pressurediary.android.mobile.ui.screens.main.graphic.StatisticsScreen
import io.pressurediary.android.mobile.ui.screens.main.history.HistoryScreen
import io.pressurediary.android.mobile.ui.screens.main.settings.SettingsScreen

@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
    globalNavController: NavHostController,
    childNavController: NavHostController,
    historyEventRefreshData: StateEvent = consumed,
    historyOnConsumedEventRefreshData: () -> Unit = {},
) {
    NavHost (
        navController = childNavController,
        startDestination = Screens.HomeBottomNavItem.History,
        route = Screens.MainScreen::class,
        modifier = modifier
    ) {
        composable<Screens.HomeBottomNavItem.History> { backStackEntry ->
            HistoryScreen(
                modifier = modifier,
                onConsumedEventRefreshData = historyOnConsumedEventRefreshData,
                eventRefreshData = historyEventRefreshData
            )
        }

        composable<Screens.HomeBottomNavItem.Settings> { backStackEntry ->
            SettingsScreen(
                modifier = modifier,
                onLogOut = {
                    globalNavController.navigate(Screens.Auth) {
                        popUpTo(Screens.MainScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Screens.HomeBottomNavItem.Statistics> { backStackEntry ->
            StatisticsScreen(
                modifier = modifier,
            )
        }
    }
}