package com.mist.pressurediary.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.curvedText
import com.mist.pressurediary.navigation.Screen

@Composable
fun PDTimeText(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val destinationName =
        if (navController.currentDestination?.route == Screen.Main.route) {
            null
        } else {
            navController.currentDestination?.route
        }

    if (destinationName == null) {
        TimeText(
            modifier = modifier
        )
    } else {
        TimeText(
            modifier = modifier,
            endCurvedContent = {
                curvedText(
                    text = destinationName
                )
            },
            endLinearContent = {
                Text(text = destinationName)
            }
        )
    }
}