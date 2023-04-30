package com.mist.pressurediary.ui.screens.create

import androidx.lifecycle.viewModelScope
import arrow.core.Option
import arrow.core.some
import com.mist.pressurediary.data.stores.PressureDiaryStore
import com.mist.pressurediary.models.PressureDiaryModel
import com.mist.pressurediary.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class CreateOrUpdateEntryState(
    val entry: PressureDiaryModel,
)

class CreateOrUpdateEntryViewModel(
    private val entry: Option<PressureDiaryModel>
) : BaseViewModel<CreateOrUpdateEntryState>() {

    override val initialState = CreateOrUpdateEntryState(
        entry = entry.fold(
            ifSome = { it },
            ifEmpty = { PressureDiaryModel() }
        )
    )

    init {
        viewModelScope.launch {
            supervisorScope {
                launch {

                }
            }
        }
    }

    //region Post Entry
    fun onPostEntry() {
        viewModelScope.launch {
            postEntry(state.entry)
        }
    }

    private suspend fun postEntry(
        entry: PressureDiaryModel
    ) = withContext(Dispatchers.IO) {
        PressureDiaryStore.addNewEntry(
            newEntry = entry.mapToTable()
        )
    }
    //endregion

    //region Update Entry
    fun onUpdateEntry(
        entry: PressureDiaryModel
    ) {
        viewModelScope.launch {
            updateEntry(entry)
        }
    }

    private suspend fun updateEntry(
        entry: PressureDiaryModel
    ) = withContext(Dispatchers.IO) {
        PressureDiaryStore.updateEntry(
            entry = entry.mapToTable()
        )
    }
    //endregion

    //region changed
    fun onDiastolicChanged(diastolic: Int) {
        state = state.copy(
            entry = state.entry.copy(
                diastolic = diastolic.some()
            )
        )
    }

    fun onSystolicChanged(systolic: Int) {
        state = state.copy(
            entry = state.entry.copy(
                systolic = systolic.some()
            )
        )
    }

    fun onPulseChanged(pulse: Int) {
        state = state.copy(
            entry = state.entry.copy(
                pulse = pulse.some()
            )
        )
    }

    fun onDateChanged(date: LocalDate) {
        state = state.copy(
            entry = state.entry.copy(
                date = date
            )
        )
    }

    fun onTimeChanged(time: LocalTime) {
        state = state.copy(
            entry = state.entry.copy(
                time = time
            )
        )
    }

    fun onCommentChanged(comment: String) {
        state = state.copy(
            entry = state.entry.copy(
                comment = comment
            )
        )
    }
    //endregion
}