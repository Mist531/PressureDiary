package com.mist.pressurediary.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.navigation.composable
import com.google.android.horologist.compose.navscaffold.WearNavScaffold
import com.mist.pressurediary.ui.screens.main.MainScreen

@Composable
fun MasterNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    WearNavScaffold(
        modifier = modifier,
        startDestination = Screen.Main.route,
        navController = navController,
        timeText = { TimeText() },
    ){
        composable(Screen.Main.route) {
            MainScreen(
                modifier = Modifier,
                onGoToCreate = {
                    navController.navigate(Screen.AddPressure.route)
                },
                onGoToHistory = {
                    navController.navigate(Screen.History.route)
                },
                onGoToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
            )
        }
        /*composable(Screen.AddPressure.route) {
            AddPressureScreen()
        }
        composable(Screen.History.route) {
            HistoryScreen()
        }*/
    }
}