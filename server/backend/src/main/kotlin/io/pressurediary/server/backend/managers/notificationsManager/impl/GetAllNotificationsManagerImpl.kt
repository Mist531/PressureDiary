package io.pressurediary.server.backend.managers.notificationsManager.impl

import io.pressurediary.server.api.models.NotificationModel
import io.pressurediary.server.backend.database.tables.Notification
import io.pressurediary.server.backend.database.tables.NotificationsTable
import io.pressurediary.server.backend.managers.notificationsManager.GetAllNotificationsManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class GetAllNotificationsManagerImpl : GetAllNotificationsManager {
    override suspend fun request(
        param: UUID,
        request: Unit
    ): List<NotificationModel> = requestTransaction {
        Notification.find {
            NotificationsTable.userUUID eq param
        }.map { notification ->
            NotificationModel(
                message = notification.message,
                timeToSend = notification.timeToSend,
                lastSentDate = notification.lastSentDate
            )
        }
    }
}
