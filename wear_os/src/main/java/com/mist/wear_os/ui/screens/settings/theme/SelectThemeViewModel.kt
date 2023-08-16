package com.mist.wear_os.ui.screens.settings.theme

import androidx.lifecycle.viewModelScope
import com.mist.common.utils.BaseViewModel
import com.mist.wear_os.data.settings.SettingsDataStore
import com.mist.wear_os.theme.Theme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

data class SelectThemeState(
    val theme: Theme? = null,
)

class SelectThemeViewModel(
    private val settingsDataStore: SettingsDataStore
) : BaseViewModel<SelectThemeState>() {
    override val initialState = SelectThemeState()

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

    //region UpdateTheme
    fun setTheme(
        theme: Theme
    ) {
        viewModelScope.launch {
            updateTheme(
                theme = theme
            )
        }
    }

    private suspend fun updateTheme(
        theme: Theme
    ) {
        settingsDataStore.updateTheme(
            theme = theme
        )
    }
    //endregion
}