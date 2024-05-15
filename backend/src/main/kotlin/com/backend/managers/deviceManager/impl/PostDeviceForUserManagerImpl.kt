package com.backend.managers.deviceManager.impl

import com.backend.database.tables.DevicesTable
import com.backend.managers.deviceManager.PostDeviceForUserManager
import com.example.api.models.PostDeviceForUserModel
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class PostDeviceForUserManagerImpl : PostDeviceForUserManager {
    override suspend operator fun invoke(param: UUID, request: PostDeviceForUserModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            DevicesTable.insert {
                it[userUUID] = param
                it[deviceType] = request.deviceType
                it[lastSyncDate] = request.lastSyncDate
            }
            HttpStatusCode.Created
        }
}