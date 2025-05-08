package io.pressurediary.server.backend.managers.pressureRecordsManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.PostPressureRecordModel
import io.pressurediary.server.backend.database.tables.PressureRecordsTable
import io.pressurediary.server.backend.managers.pressureRecordsManager.PostPressureRecordManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class PostPressureRecordManagerImpl : PostPressureRecordManager {
    override suspend fun request(
        param: UUID,
        request: PostPressureRecordModel
    ): HttpStatusCode = requestTransaction {
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