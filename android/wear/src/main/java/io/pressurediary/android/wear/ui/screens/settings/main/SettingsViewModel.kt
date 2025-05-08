package io.pressurediary.android.wear.ui.screens.settings.main

import androidx.lifecycle.viewModelScope
import io.pressurediary.android.common.utils.BaseViewModel
import io.pressurediary.android.wear.data.settings.SettingsDataStore
import io.pressurediary.android.wear.theme.Theme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class SettingsState(
    val theme: Theme? = null,
)

class SettingsViewModel(
    private val settingsDataStore: SettingsDataStore
) : BaseViewModel<SettingsState>() {
    override val initialState = SettingsState()

    init {
        viewModelScope.launch {
            launch {
                stateSettings()
            }
        }
    }

    private suspend fun stateSettings() {
        settingsDataStore.settingsFlow.collectLatest { settings ->
            state = state.copy(
                theme = Theme.valueOf(settings.themeName)
            )
        }
    }
}