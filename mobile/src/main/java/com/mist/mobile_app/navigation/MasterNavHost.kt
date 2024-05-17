package com.mist.mobile_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mist.mobile_app.ui.screens.auth.login.LoginScreen
import com.mist.mobile_app.ui.screens.auth.registration.RegistrationScreen
import com.mist.mobile_app.ui.screens.main.MainScreen
import com.mist.mobile_app.ui.screens.splash.SplashScreen

@Composable
fun MasterNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route,
        modifier = modifier
    ) {
        composable(
            route = Screens.SplashScreen.route
        ) {
            SplashScreen(
                onGoToLogin = {
                    navController.navigate(Screens.Auth.Login.route) {
                        popUpTo(Screens.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onGoToMain = {
                    navController.navigate(Screens.MainScreen.route) {
                        popUpTo(Screens.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screens.MainScreen.route
        ) {
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
    navigation(
        route = Screens.Auth.route,
        startDestination = Screens.Auth.Login.route,
    ) {
        composable(
            route = Screens.Auth.Login.route,
        ) {
            LoginScreen(
                onGoToRegistration = {
                    navController.navigate(Screens.Auth.Registration.route)
                },
                onGoToMainScreen = {
                    navController.navigate(Screens.MainScreen.route) {
                        popUpTo(Screens.Auth.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screens.Auth.Registration.route,
        ) {
            RegistrationScreen(
                onGoToLogin = {
                    navController.navigate(Screens.Auth.Login.route) {
                        popUpTo(Screens.Auth.Registration.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}