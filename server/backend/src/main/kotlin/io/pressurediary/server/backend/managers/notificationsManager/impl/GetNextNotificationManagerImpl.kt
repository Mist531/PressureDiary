package io.pressurediary.server.backend.managers.notificationsManager.impl

import io.pressurediary.server.api.models.NotificationModel
import io.pressurediary.server.backend.database.tables.Notification
import io.pressurediary.server.backend.database.tables.NotificationsTable
import io.pressurediary.server.backend.managers.notificationsManager.GetNextNotificationManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalTime
import java.util.UUID

class GetNextNotificationManagerImpl : GetNextNotificationManager {
    override suspend fun request(
        param: UUID,
        request: Unit
    ): NotificationModel = requestTransaction {
        Notification.find {
            (NotificationsTable.userUUID eq param) and
                    (NotificationsTable.timeToSend greaterEq LocalTime.now())
        }.orderBy(NotificationsTable.timeToSend to SortOrder.ASC)
            .limit(1)
            .firstOrNull()
            ?.let { notification ->
                NotificationModel(
                    message = notification.message,
                    timeToSend = notification.timeToSend,
                    lastSentDate = notification.lastSentDate
                )
            } ?: throw NoSuchElementException("Нету уведомлений")
    }
}
