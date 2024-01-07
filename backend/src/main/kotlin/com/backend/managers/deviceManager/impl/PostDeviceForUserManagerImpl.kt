package com.backend.managers.deviceManager.impl

import com.backend.database.tables.DevicesTable
import com.backend.managers.deviceManager.PostDeviceForUserManager
import com.backend.models.PostDeviceForUserModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PostDeviceForUserManagerImpl : PostDeviceForUserManager {
    override suspend operator fun invoke(param: Unit, request: PostDeviceForUserModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            DevicesTable.insert {
                it[userUUID] = request.userUUID
                it[deviceType] = request.deviceType
                it[lastSyncDate] = request.lastSyncDate
            }
            HttpStatusCode.Created
        }
}