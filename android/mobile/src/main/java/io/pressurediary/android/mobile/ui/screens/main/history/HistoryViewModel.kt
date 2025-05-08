package io.pressurediary.android.mobile.ui.screens.main.history

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import io.pressurediary.android.common.data.bd.PressureDiaryStore
import io.pressurediary.android.common.data.repository.PressureRecordRepository
import io.pressurediary.android.common.utils.BaseViewModel
import io.pressurediary.android.common.utils.errorflow.NetworkErrorFlow
import io.pressurediary.server.api.models.GetAllPressureRecordsModel
import io.pressurediary.server.api.models.GetPaginatedPressureRecordsModel
import io.pressurediary.server.api.models.PressureRecordModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime

enum class StatisticPage {
    BLOOD_PRESSURE,
    HEART_RATE
}

@Immutable
data class HistoryState(
    val pressureRecords: Map<LocalDate, List<PressureRecordModel>> = emptyMap(),
    val allPressureRecords: List<PressureRecordModel> = emptyList(),
    val fromDateTime: LocalDateTime = LocalDateTime.now().minusYears(5),
    val toDateTime: LocalDateTime? = null,
    val page: Int = 0,
    val showProgressBar: Boolean = true,
    val showProgressBarChart: Boolean = true,
    val isRefreshing: Boolean = false,
    val isCanLoadNextPage: Boolean = false,
    val selectedStatisticPage: StatisticPage = StatisticPage.BLOOD_PRESSURE
)

class HistoryViewModel(
    private val pressureRecordRepository: PressureRecordRepository
) : BaseViewModel<HistoryState>() {

    override val initialState = HistoryState()

    init {
        viewModelScope.launch {
            launch {
                onGetPressureDiary()
            }
            launch {
                getAllPressureDiary()
            }
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
                }.sortedByDescending {
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

    private suspend fun getAllPressureDiary() = withContext(Dispatchers.IO) {
        state = state.copy(
            showProgressBarChart = true
        )
        pressureRecordRepository.getAllPressureRecords(
            model = GetAllPressureRecordsModel(
                fromDateTime = state.fromDateTime,
                toDateTime = state.toDateTime ?: LocalDateTime.now(),
            )
        ).fold(
            ifLeft = { error ->
                NetworkErrorFlow.pushError(error)
            },
            ifRight = { records ->
                state = state.copy(
                    allPressureRecords = records
                        .sortedBy {
                            it.dateTimeRecord
                        },
                )
            }
        )
        state = state.copy(
            showProgressBarChart = false
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
        viewModelScope.launch {
            launch {
                getPressureDiary()
            }
            launch {
                getAllPressureDiary()
            }
        }.invokeOnCompletion {
            state = state.copy(
                isRefreshing = false
            )
        }
    }

    private val updateMutex = Mutex()

    fun nextPagePressureDiary() {
        viewModelScope.launch {
            updateMutex.withLock {
                getPressureDiary()
            }
        }
    }

    fun onSetStatisticsPage(
        statisticPage: StatisticPage
    ) {
        state = state.copy(
            selectedStatisticPage = statisticPage
        )
    }
}