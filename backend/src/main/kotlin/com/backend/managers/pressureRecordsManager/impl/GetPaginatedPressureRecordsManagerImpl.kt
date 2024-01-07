package com.backend.managers.pressureRecordsManager.impl

import com.backend.database.tables.PressureRecordsTable
import com.backend.managers.pressureRecordsManager.GetPaginatedPressureRecordsManager
import com.backend.models.GetPaginatedPressureRecordsModel
import com.backend.models.PressureRecordModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


class GetPaginatedPressureRecordsManagerImpl : GetPaginatedPressureRecordsManager {
    override suspend operator fun invoke(
        param: Unit,
        request: GetPaginatedPressureRecordsModel
    ): List<PressureRecordModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            PressureRecordsTable.select {
                (PressureRecordsTable.userUUID eq request.userUUID) and
                        (PressureRecordsTable.dateTimeRecord greaterEq request.fromDateTime) and
                        (PressureRecordsTable.dateTimeRecord lessEq request.toDateTime)
            }
                .limit(request.pageSize, offset = ((request.page - 1) * request.pageSize).toLong())
                .map(::toPressureRecord)
        }

    private fun toPressureRecord(row: ResultRow): PressureRecordModel =
        PressureRecordModel(
            pressureRecordUUID = row[PressureRecordsTable.id].value,
            userUUID = row[PressureRecordsTable.userUUID].value,
            dateTimeRecord = row[PressureRecordsTable.dateTimeRecord],
            systolic = row[PressureRecordsTable.systolic],
            diastolic = row[PressureRecordsTable.diastolic],
            pulse = row[PressureRecordsTable.pulse],
            note = row[PressureRecordsTable.note],
            deviceType = row[PressureRecordsTable.deviceType]
        )
}