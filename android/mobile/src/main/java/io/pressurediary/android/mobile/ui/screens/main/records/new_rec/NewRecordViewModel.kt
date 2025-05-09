package io.pressurediary.android.mobile.ui.screens.main.records.new_rec

import androidx.compose.runtime.Immutable
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import io.pressurediary.android.common.data.repository.PressureRecordRepository
import io.pressurediary.android.common.utils.BaseViewModel
import io.pressurediary.android.common.utils.errorflow.NetworkErrorFlow
import io.pressurediary.server.api.models.DeviceType
import io.pressurediary.server.api.models.PostPressureRecordModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

@Immutable
data class NewRecordState(
    val systolic: Int? = null,
    val isSystolicError: Boolean = false,
    val diastolic: Int? = null,
    val isDiastolicError: Boolean = false,
    val pulse: Int? = null,
    val isPulseError: Boolean = false,
    val note: String = "",
    val closeBottomSheetEvent: StateEvent = consumed,
    val showProgressBar: Boolean = false,
) {
    fun mapToPostPressureRecordModel() = PostPressureRecordModel(
        dateTimeRecord = LocalDateTime.now(),
        systolic = systolic ?: 0,
        diastolic = diastolic ?: 0,
        pulse = pulse ?: 0,
        note = note,
        deviceType = DeviceType.ANDROID
    )

    fun validateRecord() = !isPulseError && !isSystolicError && !isDiastolicError
}

class NewRecordViewModel(
    private val pressureRecordRepository: PressureRecordRepository
) : BaseViewModel<NewRecordState>() {

    override val initialState = NewRecordState()

    fun clearState() {
        state = initialState
    }

    fun setSystolic(text: String) {
        if (text.isDigitsOnly() && text.length <= 3 || text.isEmpty()) {
            val value = text.toIntOrNull()

            state = state.copy(
                systolic = value
            )
        }
    }

    fun setDiastolic(text: String) {
        if (text.isDigitsOnly() && text.length <= 3 || text.isEmpty()) {
            val value = text.toIntOrNull()

            state = state.copy(
                diastolic = value,
            )
        }
    }

    fun setPulse(text: String) {
        if (text.isDigitsOnly() && text.length <= 3 || text.isEmpty()) {
            val value = text.toIntOrNull()

            state = state.copy(
                pulse = value
            )
        }
    }

    fun setNote(text: String) {
        state = state.copy(
            note = text
        )
    }

    private fun validateValues() {
        val isPulseError = state.pulse?.let {
            it !in 20..400
        } != false

        val isDiastolicError = state.diastolic?.let {
            it !in 40..400
        } != false

        val isSystolicError = state.systolic?.let {
            it !in 40..400
        } != false

        state = state.copy(
            isSystolicError = isSystolicError,
            isPulseError = isPulseError,
            isDiastolicError = isDiastolicError
        )
    }

    fun onSaveRecord() {
        validateValues()
        if (state.validateRecord()) {
            viewModelScope.launch {
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
        pressureRecordRepository.addPressureRecord(
            model = state.mapToPostPressureRecordModel()
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
        state = state.copy(
            showProgressBar = false
        )
    }
}