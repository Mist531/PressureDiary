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
    val toDateTime: LocalDateTime? = null,
    val page: Int = 0,
    val showProgressBar: Boolean = true,
    val isRefreshing: Boolean = false,
    val isCanLoadNextPage: Boolean = false,
)

class HistoryViewModel(
    private val pressureRecordRepository: PressureRecordRepository
) : BaseViewModel<HistoryState>() {

    override val initialState = HistoryState()

    init {
        viewModelScope.launch {
            onGetPressureDiary()
        }
    }

    private suspend fun onGetPressureDiary() {
        state = state.copy(
            showProgressBar = true
        )
        getPressureDiary()
        state = state.copy(
            showProgressBar = false
        )
    }

    private val pageSize = 10

    private suspend fun getPressureDiary() = withContext(Dispatchers.IO) {
        pressureRecordRepository.getPaginatedPressureRecords(
            model = GetPaginatedPressureRecordsModel(
                fromDateTime = state.fromDateTime,
                toDateTime = state.toDateTime ?: LocalDateTime.now(),
                page = state.page,
                pageSize = pageSize
            )
        ).fold(
            ifLeft = { error ->
                NetworkErrorFlow.pushError(error)
            },
            ifRight = { records ->
                val groupRecords = (if (state.page == 0) records
                else (records + state.pressureRecords.flatMap { it.value }).distinctBy {
                    it.dateTimeRecord
                }).groupBy {
                    it.dateTimeRecord.toLocalDate()
                }

                val isCanLoadNextPage = records.size >= pageSize

                state = state.copy(
                    pressureRecords = groupRecords,
                    isCanLoadNextPage = isCanLoadNextPage,
                    page = if (isCanLoadNextPage) state.page + 1 else state.page
                )
            }
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

    fun onRefresh() {
        viewModelScope.launch {
            refresh()
        }
    }

    private suspend fun refresh() {
        state = state.copy(
            isRefreshing = true,
            page = 0
        )
        getPressureDiary()
        state = state.copy(
            isRefreshing = false
        )
    }

    fun nextPagePressureDiary() {
        viewModelScope.launch {
            getPressureDiary()
        }
    }
}