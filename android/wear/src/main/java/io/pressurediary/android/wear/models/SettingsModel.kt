package io.pressurediary.android.wear.models

import io.pressurediary.android.wear.theme.Theme
import kotlinx.serialization.Serializable

@Serializable
data class SettingsModel(
    val themeName: String = Theme.GREEN.name
)