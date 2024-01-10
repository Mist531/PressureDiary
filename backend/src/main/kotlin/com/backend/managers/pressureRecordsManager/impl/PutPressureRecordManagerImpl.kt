package com.backend.managers.pressureRecordsManager.impl

import com.backend.database.tables.PressureRecordsTable
import com.backend.managers.pressureRecordsManager.PutPressureRecordManager
import com.example.api.models.PutPressureRecordModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class PutPressureRecordManagerImpl : PutPressureRecordManager {
    override suspend operator fun invoke(param: Unit, request: PutPressureRecordModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            PressureRecordsTable.update(
                { PressureRecordsTable.id eq request.pressureRecordUUID }
            ) { update ->
                request.dateTimeRecord?.let { update[dateTimeRecord] = request.dateTimeRecord!! }
                request.systolic?.let { update[systolic] = request.systolic!! }
                request.diastolic?.let { update[diastolic] = request.diastolic!! }
                request.pulse?.let { update[pulse] = request.pulse!! }
                request.note?.let { update[note] = request.note!! }
            }
            HttpStatusCode.OK
        }
}