package io.pressurediary.android.mobile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import io.pressurediary.android.common.utils.errorflow.NetworkErrorFlow
import io.pressurediary.android.mobile.navigation.MasterNavHost
import io.pressurediary.android.mobile.ui.theme.PDTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivityMobile : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinAndroidContext {
                PDTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ErrorToast()
                        MasterNavHost()
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorToast() {
    var networkErrorEvent: StateEventWithContent<String> by remember {
        mutableStateOf(consumed())
    }

    LaunchedEffect(true) {
        NetworkErrorFlow.flow.collectLatest { error ->
            networkErrorEvent = triggered(error.message)
        }
    }

    val context = LocalContext.current

    EventEffect(
        event = networkErrorEvent,
        onConsumed = {
            networkErrorEvent = consumed()
        }
    ) { text ->
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_LONG
        ).show()
    }
}