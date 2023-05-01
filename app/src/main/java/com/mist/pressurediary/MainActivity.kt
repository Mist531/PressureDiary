/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.mist.pressurediary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.mist.pressurediary.navigation.MasterNavHost
import com.mist.pressurediary.theme.PressureDiaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PressureDiaryTheme {
                MasterApp()
            }
        }
    }
}

@Composable
fun MasterApp(
    modifier: Modifier = Modifier,
){
    val navController = rememberSwipeDismissableNavController()

    MasterNavHost(
        navController = navController,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    )
}