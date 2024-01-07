package com.backend.managers.deviceManager.impl

import com.backend.database.tables.DevicesTable
import com.backend.managers.deviceManager.DeleteUserDeviceManager
import com.backend.models.DeleteUserDeviceModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DeleteUserDeviceManagerImpl : DeleteUserDeviceManager {
    override suspend operator fun invoke(param: Unit, request: DeleteUserDeviceModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            DevicesTable.deleteWhere { DevicesTable.id eq request.deviceUUID }
            HttpStatusCode.OK
        }
}