package com.backend.managers.historyManager.impl

import com.backend.database.tables.History
import com.backend.database.tables.PressureRecordsTable
import com.backend.managers.historyManager.RestoreRecordFromHistoryManager
import com.example.api.models.HistoryModel
import com.example.api.models.RestoreHistoryModel
import io.ktor.server.plugins.NotFoundException
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import java.util.UUID

class RestoreRecordFromHistoryManagerImpl : RestoreRecordFromHistoryManager {
    override suspend fun invoke(param: UUID, request: RestoreHistoryModel): HistoryModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val history = History.findById(request.historyUUID) ?: throw NotFoundException("История не найдена")

            PressureRecordsTable.update(
                { PressureRecordsTable.id eq history.pressureRecordUUID.id }
            ) {
                it[dateTimeRecord] = history.dateTimeRecord
                it[systolic] = history.systolic
                it[diastolic] = history.diastolic
                it[pulse] = history.pulse
                it[note] = history.note
            }

            HistoryModel(
                pressureRecordUUID = history.pressureRecordUUID.id.value,
                dateTimeModified = history.dateTimeModified,
                dateTimeRecord = history.dateTimeRecord,
                systolic = history.systolic,
                diastolic = history.diastolic,
                pulse = history.pulse,
                note = history.note
            )
        }
}

