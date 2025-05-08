package io.pressurediary.android.wear.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.navigation.composable
import com.google.android.horologist.compose.navscaffold.WearNavScaffold
import io.pressurediary.android.wear.ui.common.PDTimeText
import io.pressurediary.android.wear.ui.screens.create.CreateOrUpdateEntryScreen
import io.pressurediary.android.wear.ui.screens.history.HistoryScreen
import io.pressurediary.android.wear.ui.screens.main.MainScreen
import io.pressurediary.android.wear.ui.screens.settings.main.SettingsScreen
import io.pressurediary.android.wear.ui.screens.settings.theme.SelectThemeScreen
import kotlinx.uuid.UUID

@Composable
fun MasterNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    WearNavScaffold(
        modifier = modifier,
        startDestination = Screen.Main.route,
        navController = navController,
        timeText = { modifierTime ->
            PDTimeText(
                modifier = modifierTime,
                navController = navController
            )
        },
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                modifier = Modifier,
                onGoToCreate = {
                    navController.navigate(Screen.CreatePressure.route)
                },
                onGoToHistory = {
                    navController.navigate(Screen.History.route)
                },
                onGoToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
            )
        }

        composable(Screen.CreatePressure.route) {
            CreateOrUpdateEntryScreen(
                onGoBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "${Screen.UpdatePressure.route}/{uuid}",
            arguments = listOf(
                navArgument("uuid") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("uuid")

            CreateOrUpdateEntryScreen(
                id = if (id != null) {
                    UUID(id)
                } else {
                    null
                },
                onGoBack = navController::popBackStack
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                onEntryClick = { id ->
                    navController.navigate(
                        "${Screen.UpdatePressure.route}/$id"
                    )
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                navigateToSelectTheme = {
                    navController.navigate(Screen.Settings.Theme.route)
                }
            )
        }

        composable(Screen.Settings.Theme.route) {
            SelectThemeScreen()
        }
    }
}