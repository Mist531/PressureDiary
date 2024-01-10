package com.mist.mobile_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.api.models.GetPaginatedPressureRecordsModel
import com.mist.mobile_app.ui.theme.PressureDiaryTheme
import java.time.LocalDateTime
import java.util.UUID

class MainActivityMobile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PressureDiaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val dsf = GetPaginatedPressureRecordsModel(
        userUUID = UUID.randomUUID(),
        fromDateTime = LocalDateTime.now(),
        toDateTime = LocalDateTime.now(),
        page = 1,
        pageSize = 1
    )
    Text(
        text = "Hello ${dsf.userUUID}!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PressureDiaryTheme {
        Greeting("Android")
    }
}