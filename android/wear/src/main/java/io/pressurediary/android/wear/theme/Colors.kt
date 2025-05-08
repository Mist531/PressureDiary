package io.pressurediary.android.wear.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val black: Color = Color(0xFF000000)
val white: Color = Color(0xFFFFFFFF)
val green: Color = Color(0xFF6DD58C)
val greenLight: Color = Color(0xFF24BE52)
val red = Color(0xFFEE675C)
val red4: Color = Color(0xFFEE675C).copy(alpha = 0.4f)
val purple: Color = Color(0xFF6D6DD5)

internal val greenColorPalette: Colors = Colors(
    background = black,
    primary = green,
    primaryVariant = greenLight,
    secondary = white,
    secondaryVariant = white,
    error = red,
    onError = red4,
    surface = Color(0xFF2F8772)
)

internal val redColorPalette: Colors = Colors(
    background = black,
    primary = red,
    primaryVariant = red,
    secondary = white,
    secondaryVariant = white,
    error = red,
    onError = red4,
    surface = Color.White.copy(alpha = 0.6f)
)

internal val purpleColorPalette: Colors = Colors(
    background = black,
    primary = purple,
    primaryVariant = purple,
    secondary = black,
    secondaryVariant = black,
    error = red,
    onError = red4,
    surface = Color.White.copy(alpha = 0.5f)
)