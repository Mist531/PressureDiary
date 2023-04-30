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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.SwipeToDismissBoxState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import com.google.android.horologist.compose.navscaffold.WearNavScaffold
import com.google.android.horologist.compose.navscaffold.scrollStateComposable
import com.mist.pressurediary.data.stores.PressureDiaryStore
import com.mist.pressurediary.navigation.MasterNavHost
import com.mist.pressurediary.navigation.Screen
import com.mist.pressurediary.theme.PressureDiaryTheme
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.uuid.UUID

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



//region EXAMPLES AND TEST
@Composable
fun WearApp(greetingName: String) {
    LaunchedEffect(Unit) {
        PressureDiaryStore.addNewEntry(
            PressureDiaryStore.PressureDiaryTable(
                id = UUID(),
                diastolic = 120,
                systolic = 80,
                pulse = 60,
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
                time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
                comment = "Test"
            )
        )
    }

    var text by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        delay(5000)
        text = PressureDiaryStore.getAllEntry().toString()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        TimeText()
        Greeting(greetingName = text)
    }
}

@Composable
fun Greeting(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.secondary,
        text = greetingName
    )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp("Preview Android")
}
//endregion