package com.backend.managers.notificationsManager.impl

import com.backend.database.tables.Notification
import com.backend.database.tables.NotificationsTable
import com.backend.managers.notificationsManager.GetAllNotificationsManager
import com.example.api.models.NotificationModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class GetAllNotificationsManagerImpl : GetAllNotificationsManager {
    override suspend fun invoke(param: UUID, request: Unit): List<NotificationModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            Notification.find { NotificationsTable.userUUID eq param }
                .map { notification ->
                    NotificationModel(
                        message = notification.message,
                        timeToSend = notification.timeToSend,
                        lastSentDate = notification.lastSentDate
                    )
                }
        }
}
