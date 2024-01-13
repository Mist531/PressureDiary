package com.backend.managers.notificationsManager.impl

import com.backend.database.tables.NotificationsTable
import com.backend.managers.notificationsManager.UpdateNotificationManager
import com.example.api.models.UpdateNotificationModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class UpdateNotificationManagerImpl : UpdateNotificationManager {
    override suspend fun invoke(param: Unit, request: UpdateNotificationModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            NotificationsTable.update(
                { NotificationsTable.id eq request.notificationUUID }
            ) { update ->
                request.message?.let { update[message] = it }
                request.timeToSend?.let { update[timeToSend] = it }
                request.lastSentDate?.let { update[lastSentDate] = it }
            }
            HttpStatusCode.OK
        }
}
