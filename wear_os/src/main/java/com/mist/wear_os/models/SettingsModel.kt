package com.mist.wear_os.models

import com.mist.wear_os.theme.Theme
import kotlinx.serialization.Serializable

@Serializable
data class SettingsModel(
    val Theme: Theme = com.mist.wear_os.theme.Theme.GREEN
)