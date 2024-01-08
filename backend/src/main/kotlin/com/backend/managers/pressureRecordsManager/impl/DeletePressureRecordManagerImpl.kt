package com.backend.managers.pressureRecordsManager.impl

import com.backend.database.tables.HistoryTable
import com.backend.database.tables.PressureRecordTagLinksTable
import com.backend.database.tables.PressureRecordsTable
import com.backend.managers.pressureRecordsManager.DeletePressureRecordManager
import com.backend.models.DeletePressureRecordModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DeletePressureRecordManagerImpl : DeletePressureRecordManager {
    override suspend operator fun invoke(param: Unit, request: DeletePressureRecordModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            PressureRecordsTable.deleteWhere {
                id eq request.pressureRecordUUID
            }
            PressureRecordTagLinksTable.deleteWhere {
                pressureRecordUUID eq request.pressureRecordUUID
            }
            HistoryTable.deleteWhere {
                pressureRecordUUID eq request.pressureRecordUUID
            }
            HttpStatusCode.OK
        }
}