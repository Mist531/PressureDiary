package com.mist.wear_os.data.settings

import android.content.Context
import com.mist.wear_os.theme.Theme
import kotlinx.coroutines.flow.distinctUntilChanged

class SettingsDataStore(
    context: Context
) {
    private val dataStore = context.settings

    val settingsFlow = dataStore.data.distinctUntilChanged()

    suspend fun updateTheme(
        theme: Theme
    ) {
        dataStore.updateData { settings ->
            settings.copy(
                Theme = theme
            )
        }
    }
}