package com.mist.mobile_app.ui.screens.main.history

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.api.models.GetPaginatedPressureRecordsModel
import com.example.api.models.PressureRecordModel
import com.mist.common.data.bd.PressureDiaryStore
import com.mist.common.data.repository.PressureRecordRepository
import com.mist.common.utils.BaseViewModel
import com.mist.common.utils.errorflow.NetworkErrorFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime

@Immutable
data class HistoryState(
    val pressureRecords: Map<LocalDate, List<PressureRecordModel>> = emptyMap(),
    val fromDateTime: LocalDateTime = LocalDateTime.now().minusYears(2),
    val toDateTime: LocalDateTime = LocalDateTime.now(),
    val page: Int = 1,
    val showProgressBar: Boolean = true,
)

class HistoryViewModel(
    private val pressureRecordRepository: PressureRecordRepository
) : BaseViewModel<HistoryState>() {

    override val initialState = HistoryState()

    init {
        viewModelScope.launch {
            getPressureDiary()
        }
    }

    private suspend fun getPressureDiary() = withContext(Dispatchers.IO) {
        pressureRecordRepository.getPaginatedPressureRecords(
            model = GetPaginatedPressureRecordsModel(
                fromDateTime = state.fromDateTime,
                toDateTime = state.toDateTime,
                page = state.page,
                pageSize = 10
            )
        ).fold(
            ifLeft = { error ->
                NetworkErrorFlow.pushError(error)
            },
            ifRight = { records ->
                val groupRecords = records.groupBy {
                    it.dateTimeRecord.toLocalDate()
                }

                state = state.copy(
                    pressureRecords = groupRecords
                )
            }
        )
        state = state.copy(
            showProgressBar = false
        )
    }

    private suspend fun getPressureDiaryList() = withContext(Dispatchers.IO) {
        PressureDiaryStore.getAllEntry().collectLatest { listTable ->
            val groupRecords = listTable.map {
                it.mapToPressureRecordModel()
            }.groupBy {
                it.dateTimeRecord.toLocalDate()
            }

            state = state.copy(
                pressureRecords = groupRecords,
                showProgressBar = false
            )
        }
    }
}