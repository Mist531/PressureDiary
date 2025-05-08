package io.pressurediary.android.wear.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.curvedText
import io.pressurediary.android.wear.navigation.Screen

@Composable
fun PDTimeText(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val route = navController.currentDestination?.route ?: ""

    val destinationName: String? = Screen.screensNeedShowName.runCatching {
        this.firstOrNull { screen ->
            route.contains(screen.route)
        }?.id
    }.getOrNull()?.let { id ->
        stringResource(id = id)
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