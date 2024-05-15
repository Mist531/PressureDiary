package com.mist.mobile_app.ui.screens.main.records.refactor

import androidx.compose.runtime.Immutable
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import com.example.api.models.PressureRecordModel
import com.example.api.models.PutPressureRecordModel
import com.example.api.utils.LocalDateTimeSerializer
import com.example.api.utils.UUIDSerializer
import com.mist.common.data.repository.PressureRecordRepository
import com.mist.common.utils.BaseViewModel
import com.mist.common.utils.errorflow.NetworkErrorFlow
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Immutable
@Serializable
data class RefactorPressureRecordModel(
    @Serializable(with = UUIDSerializer::class)
    val pressureRecordUUID: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateTimeRecord: LocalDateTime?,
    val systolic: Int?,
    val diastolic: Int?,
    val pulse: Int?,
    val note: String = "",
)

@Immutable
data class RefactorRecordState(
    val pressureRecord: RefactorPressureRecordModel?,
    val closeBottomSheetEvent: StateEvent = consumed
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
}

class RefactorRecordViewModel(
    pressureRecordModel: PressureRecordModel?,
    private val pressureRecordRepository: PressureRecordRepository
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
            state = state.copy(
                pressureRecord = state.pressureRecord?.copy(
                    systolic = text.toIntOrNull()
                )
            )
        }
    }

    fun setDiastolic(text: String) {
        if ((text.isDigitsOnly() && text.length <= 3) || text.isEmpty()) {
            state = state.copy(
                pressureRecord = state.pressureRecord?.copy(
                    diastolic = text.toIntOrNull()
                )
            )
        }
    }

    fun setPulse(text: String) {
        if (text.isDigitsOnly() && text.length <= 3 || text.isEmpty()) {
            state = state.copy(
                pressureRecord = state.pressureRecord?.copy(
                    pulse = text.toIntOrNull()
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

    fun onSaveRecord() {
        viewModelScope.launch {
            saveRecord()
        }
    }

    fun onConsumedBottomSheetEvent() {
        state = state.copy(
            closeBottomSheetEvent = consumed
        )
    }

    private suspend fun saveRecord() = withContext(Dispatchers.IO) {
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
    }
}