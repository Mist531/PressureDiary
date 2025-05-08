package io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.DeletePressureRecordTagLinkByRecordModel
import io.pressurediary.server.backend.database.tables.PressureRecordTagLinksTable
import io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.DeletePressureRecordTagLinkByRecordManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DeletePressureRecordTagLinkByRecordManagerImpl : DeletePressureRecordTagLinkByRecordManager {
    override suspend fun request(
        param: Unit,
        request: DeletePressureRecordTagLinkByRecordModel
    ): HttpStatusCode = requestTransaction {
        PressureRecordTagLinksTable.deleteWhere {
            pressureRecordUUID eq request.pressureRecordUUID
        }
        HttpStatusCode.OK
    }
}