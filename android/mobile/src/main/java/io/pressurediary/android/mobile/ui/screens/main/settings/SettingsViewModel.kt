package io.pressurediary.android.mobile.ui.screens.main.settings

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import io.pressurediary.android.common.data.repository.UserRepository
import io.pressurediary.android.common.data.stores.impl.TokensDataStore
import io.pressurediary.android.common.utils.BaseViewModel
import io.pressurediary.android.common.utils.errorflow.NetworkErrorFlow
import kotlinx.coroutines.launch

@Immutable
data class SettingsState(
    val logOutEvent: StateEvent = consumed,
)

class SettingsViewModel(
    private val tokensDataStore: TokensDataStore,
    private val userRepository: UserRepository,
) : BaseViewModel<SettingsState>() {

    override val initialState: SettingsState = SettingsState()

    fun onLogOut() {
        viewModelScope.launch {
            launch {
                tokensDataStore.clearData()
            }.invokeOnCompletion {
                onTriggeredLogOut()
            }
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

    fun onDeleteAcc() {
        viewModelScope.launch {
            deleteAcc()
        }
    }

    private suspend fun deleteAcc() {
        userRepository.deleteUser().fold(
            ifLeft = { error ->
                NetworkErrorFlow.pushError(error)
            },
            ifRight = {
                onLogOut()
            }
        )
    }
}