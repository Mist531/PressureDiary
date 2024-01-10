package com.backend.managers.pressureRecordsManager.impl

import com.backend.database.tables.PressureRecordsTable
import com.backend.managers.pressureRecordsManager.PostPressureRecordManager
import com.example.api.models.PostPressureRecordModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class PostPressureRecordManagerImpl : PostPressureRecordManager {
    override suspend operator fun invoke(param: UUID, request: PostPressureRecordModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            PressureRecordsTable.insert {
                it[userUUID] = param
                it[dateTimeRecord] = request.dateTimeRecord
                it[systolic] = request.systolic
                it[diastolic] = request.diastolic
                it[pulse] = request.pulse
                it[note] = request.note
                it[deviceType] = request.deviceType
            }
            HttpStatusCode.Created
        }
}