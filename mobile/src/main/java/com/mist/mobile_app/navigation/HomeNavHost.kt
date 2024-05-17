package com.mist.mobile_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mist.mobile_app.ui.screens.main.graphic.StatisticsScreen
import com.mist.mobile_app.ui.screens.main.history.HistoryScreen
import com.mist.mobile_app.ui.screens.main.settings.SettingsScreen
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed

@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
    globalNavController: NavHostController,
    childNavController: NavHostController,
    historyEventRefreshData: StateEvent = consumed,
    historyOnConsumedEventRefreshData: () -> Unit = {},
) {
    NavHost(
        navController = childNavController,
        route = Screens.MainScreen.route,
        startDestination = Screens.HomeBottomNavItem.History.route,
        modifier = modifier
    ) {

        composable(Screens.HomeBottomNavItem.History.route) { backStackEntry ->
            HistoryScreen(
                modifier = modifier,
                onConsumedEventRefreshData = historyOnConsumedEventRefreshData,
                eventRefreshData = historyEventRefreshData
            )
        }

        composable(Screens.HomeBottomNavItem.Settings.route) { backStackEntry ->
            SettingsScreen(
                modifier = modifier,
                onLogOut = {
                    globalNavController.navigate(Screens.Auth.route) {
                        popUpTo(Screens.MainScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screens.HomeBottomNavItem.Statistics.route) { backStackEntry ->
            StatisticsScreen(
                modifier = modifier,
            )
        }
    }
}