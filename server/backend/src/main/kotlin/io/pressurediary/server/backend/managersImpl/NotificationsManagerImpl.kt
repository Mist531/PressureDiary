package io.pressurediary.server.backend.managersImpl

import io.pressurediary.server.backend.managers.notificationsManager.GetAllNotificationsManager
import io.pressurediary.server.backend.managers.notificationsManager.GetNextNotificationManager
import io.pressurediary.server.backend.managers.notificationsManager.UpdateNotificationManager
import io.pressurediary.server.api.models.NotificationModel
import io.pressurediary.server.api.models.UpdateNotificationModel
import io.ktor.http.HttpStatusCode
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

interface NotificationsManager {
    suspend fun getAllNotifications(userId: UUID): List<NotificationModel>
    suspend fun updateNotification(model: UpdateNotificationModel): HttpStatusCode
    suspend fun getNextNotification(userId: UUID): NotificationModel
}

class NotificationsManagerImpl : NotificationsManager, KoinComponent {

    override suspend fun getAllNotifications(userId: UUID): List<NotificationModel> {
        val manager: GetAllNotificationsManager by inject()
        return runCatching {
            manager.request(userId, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun updateNotification(model: UpdateNotificationModel): HttpStatusCode {
        val manager: UpdateNotificationManager by inject()
        return runCatching {
            manager.request(Unit, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun getNextNotification(userId: UUID): NotificationModel {
        val manager: GetNextNotificationManager by inject()
        return runCatching {
            manager.request(userId, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }
}
