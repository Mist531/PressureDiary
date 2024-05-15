package com.mist.mobile_app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    parentPaddingValues: PaddingValues = PaddingValues(),
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
            //TODO
            Box(
                modifier = modifier
                    .padding(
                        parentPaddingValues
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Statistics")
            }
        }
    }
}