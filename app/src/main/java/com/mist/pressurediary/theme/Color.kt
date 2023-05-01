package com.mist.pressurediary.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val black: Color = Color(0xFF000000)
val white: Color = Color(0xFFFFFFFF)
val green: Color = Color(0xFF6DD58C)
val greenLight: Color = Color(0xFF24BE52)
val red = Color(0xFFEE675C)
val red4: Color = Color(0xFFEE675C).copy(alpha = 0.4f)
val placeholderColor: Color = Color(0xFF2F8772)

internal val wearColorPalette: Colors = Colors(
    background = black,
    primary = green,
    primaryVariant = greenLight,
    secondary = white,
    secondaryVariant = white,
    error = red,
    onError = red4,
    surface = placeholderColor
)