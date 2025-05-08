package io.pressurediary.server.backend.managers.pressureRecordsManager.impl

import io.pressurediary.server.api.models.GetPaginatedPressureRecordsModel
import io.pressurediary.server.api.models.PressureRecordModel
import io.pressurediary.server.backend.database.tables.PressureRecordsTable
import io.pressurediary.server.backend.managers.pressureRecordsManager.GetPaginatedPressureRecordsManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID


class GetPaginatedPressureRecordsManagerImpl : GetPaginatedPressureRecordsManager {
    override suspend fun request(
        param: UUID,
        request: GetPaginatedPressureRecordsModel
    ): List<PressureRecordModel> = requestTransaction {
        PressureRecordsTable.selectAll().where {
            (PressureRecordsTable.userUUID eq param) and
                    (PressureRecordsTable.dateTimeRecord greaterEq request.fromDateTime) and
                    (PressureRecordsTable.dateTimeRecord lessEq request.toDateTime)
        }.orderBy(PressureRecordsTable.dateTimeRecord to SortOrder.DESC)
            .limit(
                n = request.pageSize,
                offset = ((request.page - 1) * request.pageSize).toLong()
            ).map(::toPressureRecord)
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