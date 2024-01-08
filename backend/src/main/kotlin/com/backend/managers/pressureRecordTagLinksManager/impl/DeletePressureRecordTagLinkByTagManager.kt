package com.backend.managers.pressureRecordTagLinksManager.impl

import com.backend.database.tables.PressureRecordTagLinksTable
import com.backend.managers.pressureRecordTagLinksManager.DeletePressureRecordTagLinkByTagManager
import com.backend.models.DeletePressureRecordTagLinkByTagModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DeletePressureRecordTagLinkByTagManagerImpl : DeletePressureRecordTagLinkByTagManager {
    override suspend operator fun invoke(param: Unit, request: DeletePressureRecordTagLinkByTagModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            PressureRecordTagLinksTable.deleteWhere {
                tagUUID eq request.tagUUID
            }
            HttpStatusCode.OK
        }
}