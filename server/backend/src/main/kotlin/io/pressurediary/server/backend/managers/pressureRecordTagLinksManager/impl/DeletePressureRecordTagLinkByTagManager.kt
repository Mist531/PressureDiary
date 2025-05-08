package io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.DeletePressureRecordTagLinkByTagModel
import io.pressurediary.server.backend.database.tables.PressureRecordTagLinksTable
import io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.DeletePressureRecordTagLinkByTagManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DeletePressureRecordTagLinkByTagManagerImpl : DeletePressureRecordTagLinkByTagManager {
    override suspend fun request(
        param: Unit,
        request: DeletePressureRecordTagLinkByTagModel
    ): HttpStatusCode = requestTransaction {
        PressureRecordTagLinksTable.deleteWhere {
            tagUUID eq request.tagUUID
        }
        HttpStatusCode.OK
    }
}