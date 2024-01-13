package com.backend.managers.historyManager.impl

import com.backend.database.tables.History
import com.backend.database.tables.HistoryTable
import com.backend.managers.historyManager.GetHistoryForRecordManager
import com.example.api.models.GetHistoryPressureRecordModel
import com.example.api.models.HistoryModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class GetHistoryForRecordManagerImpl : GetHistoryForRecordManager {
    override suspend fun invoke(param: GetHistoryPressureRecordModel, request: Unit): List<HistoryModel> =
        newSuspendedTransaction(Dispatchers.IO) {
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
