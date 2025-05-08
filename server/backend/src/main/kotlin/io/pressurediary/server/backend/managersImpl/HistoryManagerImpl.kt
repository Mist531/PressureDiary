package io.pressurediary.server.backend.managersImpl

import io.pressurediary.server.backend.managers.historyManager.GetHistoryForRecordManager
import io.pressurediary.server.backend.managers.historyManager.RestoreRecordFromHistoryManager
import io.pressurediary.server.api.models.GetHistoryPressureRecordModel
import io.pressurediary.server.api.models.HistoryModel
import io.pressurediary.server.api.models.RestoreHistoryModel
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
            manager.request(getHistoryPressureRecordModel, Unit)
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
            manager.request(userId, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }
}
