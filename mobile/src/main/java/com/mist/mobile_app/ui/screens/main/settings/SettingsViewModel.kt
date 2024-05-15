package com.mist.mobile_app.ui.screens.main.settings

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.mist.common.data.stores.impl.TokensDataStore
import com.mist.common.utils.BaseViewModel
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Immutable
data class SettingsState(
    val logOutEvent: StateEvent = consumed,
)

class SettingsViewModel(
    private val tokensDataStore: TokensDataStore
) : BaseViewModel<SettingsState>() {

    override val initialState: SettingsState = SettingsState()

    fun logOut() {
        viewModelScope.launch {
            async {
                tokensDataStore.clearData()
            }.await()
            onTriggeredLogOut()
        }
    }

    private fun onTriggeredLogOut() {
        state = state.copy(
            logOutEvent = triggered
        )
    }

    fun onConsumedLogOut() {
        state = state.copy(
            logOutEvent = consumed
        )
    }
}