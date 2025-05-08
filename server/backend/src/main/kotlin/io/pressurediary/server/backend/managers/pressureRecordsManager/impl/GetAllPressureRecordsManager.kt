package io.pressurediary.server.backend.managers.pressureRecordsManager.impl

import io.pressurediary.server.api.models.GetAllPressureRecordsModel
import io.pressurediary.server.api.models.PressureRecordModel
import io.pressurediary.server.backend.database.tables.PressureRecordsTable
import io.pressurediary.server.backend.managers.pressureRecordsManager.GetAllPressureRecordsManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class GetAllPressureRecordsManagerImpl : GetAllPressureRecordsManager {
    override suspend fun request(
        param: UUID,
        request: GetAllPressureRecordsModel
    ): List<PressureRecordModel> = requestTransaction {
        PressureRecordsTable.selectAll().where {
            (PressureRecordsTable.userUUID eq param) and
                    (PressureRecordsTable.dateTimeRecord greaterEq request.fromDateTime) and
                    (PressureRecordsTable.dateTimeRecord lessEq request.toDateTime)
        }.orderBy(PressureRecordsTable.dateTimeRecord to SortOrder.DESC)
            .map(::toPressureRecord)
    }

    private fun toPressureRecord(
        row: ResultRow
    ): PressureRecordModel = PressureRecordModel(
        pressureRecordUUID = row[PressureRecordsTable.id].value,
        dateTimeRecord = row[PressureRecordsTable.dateTimeRecord],
        systolic = row[PressureRecordsTable.systolic],
        diastolic = row[PressureRecordsTable.diastolic],
        pulse = row[PressureRecordsTable.pulse],
        note = row[PressureRecordsTable.note],
    )
}