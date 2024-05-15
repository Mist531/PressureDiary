package com.backend.managers.pressureRecordTagLinksManager.impl

import com.backend.database.tables.PressureRecordTagLinksTable
import com.backend.managers.pressureRecordTagLinksManager.DeletePressureRecordTagLinkByRecordManager
import com.example.api.models.DeletePressureRecordTagLinkByRecordModel
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DeletePressureRecordTagLinkByRecordManagerImpl : DeletePressureRecordTagLinkByRecordManager {
    override suspend operator fun invoke(param: Unit, request: DeletePressureRecordTagLinkByRecordModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            PressureRecordTagLinksTable.deleteWhere {
                pressureRecordUUID eq request.pressureRecordUUID
            }
            HttpStatusCode.OK
        }
}