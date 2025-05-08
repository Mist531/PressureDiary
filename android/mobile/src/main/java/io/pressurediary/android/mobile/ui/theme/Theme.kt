package io.pressurediary.android.mobile.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import io.pressurediary.android.common.ui.PDColors

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