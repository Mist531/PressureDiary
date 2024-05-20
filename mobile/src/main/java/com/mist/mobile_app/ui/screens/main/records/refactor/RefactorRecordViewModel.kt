package com.mist.mobile_app.ui.screens.main.records.refactor

import androidx.compose.runtime.Immutable
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import com.example.api.models.DeletePressureRecordModel
import com.example.api.models.PressureRecordModel
import com.example.api.models.PutPressureRecordModel
import com.mist.common.data.repository.PressureRecordRepository
import com.mist.common.utils.BaseViewModel
import com.mist.common.utils.errorflow.NetworkErrorFlow
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.UUID

@Immutable
data class RefactorPressureRecordModel(
    val pressureRecordUUID: UUID,
    val dateTimeRecord: LocalDateTime?,
    val systolic: Int?,
    val diastolic: Int?,
    val pulse: Int?,
    val note: String = "",
)

@Immutable
data class RefactorRecordState(
    val pressureRecord: RefactorPressureRecordModel?,
    val showProgressBar: Boolean = false,
    val isSystolicError: Boolean = false,
    val isDiastolicError: Boolean = false,
    val isPulseError: Boolean = false,
    val closeBottomSheetEvent: StateEvent = consumed,
    val logOutEvent: StateEvent = consumed,
) {
    fun mapToPutPressureRecordModel() = pressureRecord?.let {
        PutPressureRecordModel(
            pressureRecordUUID = pressureRecord.pressureRecordUUID,
            dateTimeRecord = pressureRecord.dateTimeRecord,
            systolic = pressureRecord.systolic,
            diastolic = pressureRecord.diastolic,
            pulse = pressureRecord.pulse,
            note = pressureRecord.note
        )
    }

    fun mapToDeletePressureRecordModel() = pressureRecord?.let {
        DeletePressureRecordModel(
            pressureRecordUUID = pressureRecord.pressureRecordUUID
        )
    }

    fun validateRecord() = !isPulseError && !isSystolicError && !isDiastolicError
}

class RefactorRecordViewModel(
    pressureRecordModel: PressureRecordModel?,
    private val pressureRecordRepository: PressureRecordRepository,
) : BaseViewModel<RefactorRecordState>() {

    override val initialState = RefactorRecordState(
        pressureRecord = pressureRecordModel?.let {
            RefactorPressureRecordModel(
                pressureRecordUUID = it.pressureRecordUUID,
                dateTimeRecord = it.dateTimeRecord,
                systolic = it.systolic,
                diastolic = it.diastolic,
                pulse = it.pulse,
                note = it.note,
            )
        }
    )

    fun setSystolic(text: String) {
        if (text.isDigitsOnly() && text.length <= 3 || text.isEmpty()) {
            val value = text.toIntOrNull()

            state = state.copy(
                pressureRecord = state.pressureRecord?.copy(
                    systolic = value
                )
            )
        }
    }

    fun setDiastolic(text: String) {
        if ((text.isDigitsOnly() && text.length <= 3) || text.isEmpty()) {
            val value = text.toIntOrNull()

            state = state.copy(
                pressureRecord = state.pressureRecord?.copy(
                    diastolic = value
                )
            )
        }
    }

    fun setPulse(text: String) {
        if (text.isDigitsOnly() && text.length <= 3 || text.isEmpty()) {
            val value = text.toIntOrNull()

            state = state.copy(
                pressureRecord = state.pressureRecord?.copy(
                    pulse = value
                )
            )
        }
    }

    fun setNote(text: String) {
        state = state.copy(
            pressureRecord = state.pressureRecord?.copy(
                note = text
            )
        )
    }

    private fun validateValues() {
        val isPulseError = state.pressureRecord?.pulse?.let {
            it !in 20..400
        } ?: true

        val isDiastolicError = state.pressureRecord?.diastolic?.let {
            it !in 40..400
        } ?: true

        val isSystolicError = state.pressureRecord?.systolic?.let {
            it !in 40..400
        } ?: true

        state = state.copy(
            isSystolicError = isSystolicError,
            isPulseError = isPulseError,
            isDiastolicError = isDiastolicError
        )
    }

    fun onSaveRecord() {
        viewModelScope.launch {
            validateValues()
            if (state.validateRecord()) {
                saveRecord()
            }
        }
    }

    fun onConsumedBottomSheetEvent() {
        state = state.copy(
            closeBottomSheetEvent = consumed
        )
    }

    private suspend fun saveRecord() = withContext(Dispatchers.IO) {
        state = state.copy(
            showProgressBar = true
        )
        state.mapToPutPressureRecordModel()?.also { model ->
            pressureRecordRepository.editPressureRecord(
                model = model
            ).fold(
                ifLeft = { error ->
                    NetworkErrorFlow.pushError(error)
                },
                ifRight = {
                    state = state.copy(
                        closeBottomSheetEvent = triggered
                    )
                }
            )
        }
        state = state.copy(
            showProgressBar = false
        )
    }

    fun onDeleteRecord() {
        viewModelScope.launch {
            deleteRecord()
        }
    }

    private suspend fun deleteRecord() {
        state.mapToDeletePressureRecordModel()?.let {
            pressureRecordRepository.deletePressureRecord(
                model = it
            ).fold(
                ifLeft = { error ->
                    NetworkErrorFlow.pushError(error)
                },
                ifRight = {
                    state = state.copy(
                        closeBottomSheetEvent = triggered
                    )
                }
            )
        }
    }
}