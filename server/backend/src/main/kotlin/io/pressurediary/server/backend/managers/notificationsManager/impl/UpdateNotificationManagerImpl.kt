package io.pressurediary.server.backend.managers.notificationsManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.UpdateNotificationModel
import io.pressurediary.server.backend.database.tables.NotificationsTable
import io.pressurediary.server.backend.managers.notificationsManager.UpdateNotificationManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class UpdateNotificationManagerImpl : UpdateNotificationManager {
    override suspend fun request(
        param: Unit,
        request: UpdateNotificationModel
    ): HttpStatusCode = requestTransaction {
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
