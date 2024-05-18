package com.backend.managersImpl

import com.backend.managers.historyManager.GetHistoryForRecordManager
import com.backend.managers.historyManager.RestoreRecordFromHistoryManager
import com.example.api.models.GetHistoryPressureRecordModel
import com.example.api.models.HistoryModel
import com.example.api.models.RestoreHistoryModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

interface HistoryManager {
    suspend fun getHistoryForRecordManagerImpl(
        getHistoryPressureRecordModel: GetHistoryPressureRecordModel
    ): List<HistoryModel>

    suspend fun restoreRecordFromHistory(
        userId: UUID,
        model: RestoreHistoryModel
    ): HistoryModel
}

class HistoryManagerImpl : HistoryManager, KoinComponent {
    override suspend fun getHistoryForRecordManagerImpl(getHistoryPressureRecordModel: GetHistoryPressureRecordModel): List<HistoryModel> {
        val manager: GetHistoryForRecordManager by inject()
        return runCatching {
            manager.invoke(getHistoryPressureRecordModel, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun restoreRecordFromHistory(
        userId: UUID,
        model: RestoreHistoryModel
    ): HistoryModel {
        val manager: RestoreRecordFromHistoryManager by inject()
        return runCatching {
            manager.invoke(userId, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }
}
