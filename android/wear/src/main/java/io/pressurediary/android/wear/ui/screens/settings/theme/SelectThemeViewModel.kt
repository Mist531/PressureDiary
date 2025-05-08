package io.pressurediary.android.wear.ui.screens.settings.theme

import androidx.lifecycle.viewModelScope
import io.pressurediary.android.common.utils.BaseViewModel
import io.pressurediary.android.wear.data.settings.SettingsDataStore
import io.pressurediary.android.wear.theme.Theme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class SelectThemeState(
    val theme: Theme? = null,
)

class SelectThemeViewModel(
    private val settingsDataStore: SettingsDataStore
) : BaseViewModel<SelectThemeState>() {
    override val initialState = SelectThemeState()

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