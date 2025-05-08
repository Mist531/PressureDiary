package io.pressurediary.server.backend.managers.pressureRecordsManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.DeletePressureRecordModel
import io.pressurediary.server.backend.database.tables.PressureRecordsTable
import io.pressurediary.server.backend.managers.pressureRecordsManager.DeletePressureRecordManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DeletePressureRecordManagerImpl : DeletePressureRecordManager {
    override suspend fun request(
        param: Unit,
        request: DeletePressureRecordModel
    ): HttpStatusCode = requestTransaction {
        PressureRecordsTable.deleteWhere {
            id eq request.pressureRecordUUID
        }
        HttpStatusCode.OK
    }
}