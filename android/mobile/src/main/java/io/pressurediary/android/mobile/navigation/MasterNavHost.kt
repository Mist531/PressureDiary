package io.pressurediary.android.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import io.pressurediary.android.mobile.ui.screens.auth.login.LoginScreen
import io.pressurediary.android.mobile.ui.screens.auth.registration.RegistrationScreen
import io.pressurediary.android.mobile.ui.screens.main.MainScreen
import io.pressurediary.android.mobile.ui.screens.splash.SplashScreen

@Composable
fun MasterNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen,
        modifier = modifier
    ) {
        composable<Screens.SplashScreen> {
            SplashScreen(
                onGoToLogin = {
                    navController.navigate(Screens.Auth.Login) {
                        popUpTo(Screens.SplashScreen) {
                            inclusive = true
                        }
                    }
                },
                onGoToMain = {
                    navController.navigate(Screens.MainScreen) {
                        popUpTo(Screens.SplashScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Screens.MainScreen> {
            MainScreen(
                navController = navController
            )
        }

        authGraph(
            navController
        )
    }
}

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
) {
    navigation<Screens.Auth>(
        startDestination = Screens.Auth.Login,
    ) {
        composable<Screens.Auth.Login> {
            LoginScreen(
                onGoToRegistration = {
                    navController.navigate(Screens.Auth.Registration)
                },
                onGoToMainScreen = {
                    navController.navigate(Screens.MainScreen) {
                        popUpTo(Screens.Auth.Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Screens.Auth.Registration> {
            RegistrationScreen(
                onGoToLogin = {
                    navController.navigate(Screens.Auth.Login) {
                        popUpTo(Screens.Auth.Registration) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}