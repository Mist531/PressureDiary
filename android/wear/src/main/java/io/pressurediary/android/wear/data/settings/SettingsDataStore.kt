package io.pressurediary.android.wear.data.settings

import android.content.Context
import io.pressurediary.android.wear.theme.Theme
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
                themeName = theme.name
            )
        }
    }
}