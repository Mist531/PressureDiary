package io.pressurediary.server.backend.managers.deviceManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.DeleteUserDeviceModel
import io.pressurediary.server.backend.database.tables.DevicesTable
import io.pressurediary.server.backend.managers.deviceManager.DeleteUserDeviceManager
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class DeleteUserDeviceManagerImpl : DeleteUserDeviceManager {
    override suspend fun request(
        param: Unit,
        request: DeleteUserDeviceModel
    ): HttpStatusCode = requestTransaction {
        DevicesTable.deleteWhere {
            id eq request.deviceUUID
        }
        HttpStatusCode.OK
    }
}