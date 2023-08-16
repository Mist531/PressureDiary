package com.mist.wear_os.ui.screens.settings.main

import androidx.lifecycle.viewModelScope
import com.mist.common.utils.BaseViewModel
import com.mist.wear_os.data.settings.SettingsDataStore
import com.mist.wear_os.theme.Theme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

data class SettingsState(
    val theme: Theme? = null,
)

class SettingsViewModel(
    private val settingsDataStore: SettingsDataStore
) : BaseViewModel<SettingsState>() {
    override val initialState = SettingsState()

    init {
        viewModelScope.launch {
            supervisorScope {
                launch {
                    stateSettings()
                }
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