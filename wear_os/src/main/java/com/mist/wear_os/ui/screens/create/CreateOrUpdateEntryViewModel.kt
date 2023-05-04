package com.mist.wear_os.ui.screens.create

import androidx.lifecycle.viewModelScope
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.mist.common.data.stores.PressureDiaryStore
import com.mist.common.models.PressureDiaryModel
import com.mist.common.utils.BaseViewModel
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.uuid.UUID
import java.time.LocalDate
import java.time.LocalTime

data class CreateOrUpdateEntryState(
    val entry: PressureDiaryModel = PressureDiaryModel(),
    val datePickerEvent: StateEvent = consumed,
    val timePickerEvent: StateEvent = consumed,
    val saveEvent: StateEvent = consumed,
    val deleteEvent: StateEvent = consumed,
    val updateEvent: StateEvent = consumed
)

class CreateOrUpdateEntryViewModel(
    private val id: Option<UUID> = none()
) : BaseViewModel<CreateOrUpdateEntryState>() {

    override val initialState = CreateOrUpdateEntryState()

    init {
        viewModelScope.launch {
            supervisorScope {
                launch {
                    id.fold(
                        ifEmpty = {},
                        ifSome = { id ->
                            getPressureDiary(id)
                        }
                    )
                }
            }
        }
    }

    //region Get Entry
    private suspend fun getPressureDiary(
        id: UUID
    ) {
        val entry = PressureDiaryStore.getEntryById(id)
        state = state.copy(
            entry = entry
        )
    }
    //endregion

    //region DatePickerEvent
    fun onDatePickerEvent() {
        state = state.copy(
            datePickerEvent = triggered
        )
    }

    fun onConsumedDatePickerEventWithSave(
        localDate: LocalDate
    ) {
        onDateChanged(localDate)
        state = state.copy(
            datePickerEvent = consumed
        )
    }
    //endregion

    //region TimePickerEvent
    fun onTimePickerEvent() {
        state = state.copy(
            timePickerEvent = triggered
        )
    }

    fun onConsumedTimePickerEventWithSave(
        localTime: LocalTime
    ) {
        onTimeChanged(localTime)
        state = state.copy(
            timePickerEvent = consumed
        )
    }
    //endregion

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
    fun onUpdateEntry() {
        viewModelScope.launch {
            updateEntry(state.entry)
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

    //region DeleteEntry
    fun onDeleteEntry() {
        viewModelScope.launch {
            deleteEntry(state.entry)
        }
    }

    private suspend fun deleteEntry(
        entry: PressureDiaryModel
    ) = withContext(Dispatchers.IO) {
        PressureDiaryStore.deleteEntry(
            entry = entry.mapToTable()
        )
    }
    //endregion

    //region SaveEvent
    fun onSaveEvent() {
        state = state.copy(
            saveEvent = triggered
        )
    }

    fun onConsumedSaveEvent() {
        state = state.copy(
            saveEvent = consumed
        )
    }
    //endregion

    //region DeleteEvent
    fun onDeleteEvent() {
        state = state.copy(
            deleteEvent = triggered
        )
    }

    fun onConsumedDeleteEvent() {
        state = state.copy(
            deleteEvent = consumed
        )
    }
    //endregion

    //region UpdateEvent
    fun onUpdateEvent() {
        state = state.copy(
            updateEvent = triggered
        )
    }

    fun onConsumedUpdateEvent() {
        state = state.copy(
            updateEvent = consumed
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

    private fun onDateChanged(date: LocalDate) {
        state = state.copy(
            entry = state.entry.copy(
                date = date
            )
        )
    }

    private fun onTimeChanged(time: LocalTime) {
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