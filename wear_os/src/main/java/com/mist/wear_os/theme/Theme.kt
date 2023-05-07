package com.mist.wear_os.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme
import com.mist.wear_os.data.settings.SettingsDataStore
import kotlinx.coroutines.flow.collectLatest

enum class Theme(
    val title: String,
    val colors: Colors
) {
    GREEN(
        title = "Green",
        colors = greenColorPalette
    ),
    RED(
        title = "Red",
        colors = redColorPalette
    ),
    PURPLE(
        title = "Purple",
        colors = purpleColorPalette
    );

    companion object {
        val listTheme: List<Theme> = listOf(
            GREEN,
            RED,
            PURPLE
        )
    }
}

@Composable
fun PressureDiaryTheme(
    settingsDataStore: SettingsDataStore,
    content: @Composable () -> Unit
) {
    var theme by remember {
        mutableStateOf<Theme?>(null)
    }

    LaunchedEffect(Unit) {
        settingsDataStore.settingsFlow.collectLatest { settings ->
            theme = settings.Theme
        }
    }

    if (theme != null) {
        MaterialTheme(
            colors = theme!!.colors,
            typography = Typography,
            content = content
        )
    }
}