package com.mist.mobile_app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mist.common.ui.PDColors
import java.time.LocalDate

@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
    globalNavController: NavHostController,
    childNavController: NavHostController,
    parentPaddingValues: PaddingValues = PaddingValues(),
) {
    NavHost(
        navController = childNavController,
        route = Screens.MainScreen.route,
        startDestination = Screens.HomeBottomNavItem.History.route,
        modifier = modifier
    ) {

        composable(Screens.HomeBottomNavItem.History.route) { backStackEntry ->
            Box(
                modifier = modifier
                    .padding(
                        parentPaddingValues
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "History")
            }
        }

        composable(Screens.HomeBottomNavItem.Settings.route) { backStackEntry ->
            Box(
                modifier = modifier
                    .padding(
                        parentPaddingValues
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Settings")
            }
        }

        composable(Screens.HomeBottomNavItem.Statistics.route) { backStackEntry ->
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