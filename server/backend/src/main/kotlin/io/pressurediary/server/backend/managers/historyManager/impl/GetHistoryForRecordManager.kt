package io.pressurediary.server.backend.managers.historyManager.impl

import io.pressurediary.server.api.models.GetHistoryPressureRecordModel
import io.pressurediary.server.api.models.HistoryModel
import io.pressurediary.server.backend.database.tables.History
import io.pressurediary.server.backend.database.tables.HistoryTable
import io.pressurediary.server.backend.managers.historyManager.GetHistoryForRecordManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class GetHistoryForRecordManagerImpl : GetHistoryForRecordManager {
    override suspend fun request(
        param: GetHistoryPressureRecordModel,
        request: Unit
    ): List<HistoryModel> = requestTransaction {
        History.find {
            HistoryTable.pressureRecordUUID eq param.pressureRecordUUID
        }.orderBy(HistoryTable.dateTimeModified to SortOrder.DESC)
            .map {
                HistoryModel(
                    pressureRecordUUID = it.pressureRecordUUID.id.value,
                    dateTimeModified = it.dateTimeModified,
                    dateTimeRecord = it.dateTimeRecord,
                    systolic = it.systolic,
                    diastolic = it.diastolic,
                    pulse = it.pulse,
                    note = it.note
                )
            }
    }
}
