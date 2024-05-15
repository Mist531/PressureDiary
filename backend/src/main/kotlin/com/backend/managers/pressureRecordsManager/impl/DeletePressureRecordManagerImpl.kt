package com.backend.managers.pressureRecordsManager.impl

import com.backend.database.tables.PressureRecordsTable
import com.backend.managers.pressureRecordsManager.DeletePressureRecordManager
import com.example.api.models.DeletePressureRecordModel
import io.ktor.http.HttpStatusCode
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
            HttpStatusCode.OK
        }
}