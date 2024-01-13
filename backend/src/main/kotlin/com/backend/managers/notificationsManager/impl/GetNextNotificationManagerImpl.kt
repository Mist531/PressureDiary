package com.backend.managers.notificationsManager.impl

import com.backend.database.tables.Notification
import com.backend.database.tables.NotificationsTable
import com.backend.managers.notificationsManager.GetNextNotificationManager
import com.example.api.models.NotificationModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalTime
import java.util.*

class GetNextNotificationManagerImpl : GetNextNotificationManager {
    override suspend fun invoke(param: UUID, request: Unit): NotificationModel =
        newSuspendedTransaction(Dispatchers.IO) {
            Notification.find {
                (NotificationsTable.userUUID eq param) and
                        (NotificationsTable.timeToSend greaterEq LocalTime.now())
            }
                .orderBy(NotificationsTable.timeToSend to SortOrder.ASC)
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
