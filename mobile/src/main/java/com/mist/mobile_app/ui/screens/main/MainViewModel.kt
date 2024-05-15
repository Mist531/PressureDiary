package com.mist.mobile_app.ui.screens.main

import com.mist.common.data.repository.PressureRecordRepository
import com.mist.common.utils.BaseViewModel
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered

data class MainState(
    val eventRefreshData: StateEvent = consumed
)

class MainViewModel : BaseViewModel<MainState>() {

    override val initialState: MainState = MainState()

    fun onTriggeredRefreshData() {
        state = state.copy(
            eventRefreshData = triggered
        )
    }

    fun onConsumedRefreshData() {
        state = state.copy(
            eventRefreshData = consumed
        )
    }
}