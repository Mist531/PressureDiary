package com.mist.mobile_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.mist.common.ui.PDColors

private val LightColorScheme = lightColorScheme(
    background = PDColors.white
)

@Composable
fun PDTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}