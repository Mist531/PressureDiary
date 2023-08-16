package com.mist.wear_os.theme

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme
import com.mist.wear_os.R
import com.mist.wear_os.data.settings.SettingsDataStore
import kotlinx.coroutines.flow.collectLatest

enum class Theme(
    @StringRes val title: Int,
    val palette: Colors
) {
    GREEN(
        title = R.string.theme_green,
        palette = greenColorPalette
    ),
    RED(
        title = R.string.theme_red,
        palette = redColorPalette
    ),
    PURPLE(
        title = R.string.theme_purple,
        palette = purpleColorPalette
    )
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
            theme = Theme.valueOf(settings.themeName)
        }
    }

    if (theme != null) {
        MaterialTheme(
            colors = theme!!.palette,
            typography = Typography,
            content = content
        )
    }
}