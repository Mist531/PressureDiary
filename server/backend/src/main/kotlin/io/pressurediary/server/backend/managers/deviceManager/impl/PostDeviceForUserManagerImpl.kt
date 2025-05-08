package io.pressurediary.server.backend.managers.deviceManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.PostDeviceForUserModel
import io.pressurediary.server.backend.database.tables.DevicesTable
import io.pressurediary.server.backend.managers.deviceManager.PostDeviceForUserManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class PostDeviceForUserManagerImpl : PostDeviceForUserManager {
    override suspend fun request(
        param: UUID,
        request: PostDeviceForUserModel
    ): HttpStatusCode = requestTransaction {
        DevicesTable.insert {
            it[userUUID] = param
            it[deviceType] = request.deviceType
            it[lastSyncDate] = request.lastSyncDate
        }
        HttpStatusCode.Created
    }
}