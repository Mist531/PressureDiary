package com.mist.wear_os.ui.screens.history

import androidx.lifecycle.viewModelScope
import com.mist.common.data.bd.PressureDiaryStore
import com.mist.common.models.PressureDiaryModel
import com.mist.common.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import java.time.LocalDate

data class HistoryState(
    val groupList: Map<LocalDate, List<PressureDiaryModel>> = mapOf(),
    val showProgressBar: Boolean = true,
)

class HistoryViewModel : BaseViewModel<HistoryState>() {

    override val initialState = HistoryState()

    init {
        viewModelScope.launch {
            supervisorScope {
                launch {
                    getPressureDiaryList()
                }
            }
        }
    }

    private suspend fun getPressureDiaryList() = withContext(Dispatchers.IO) {
        PressureDiaryStore.getAllEntry().collectLatest { listTable ->
            val groupList = listTable.map { it.mapToModel() }.groupBy { it.date }
            state = state.copy(
                groupList = groupList,
                showProgressBar = false
            )
        }
    }
}