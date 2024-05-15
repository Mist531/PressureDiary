package com.backend.managersImpl

import com.backend.managers.notificationsManager.GetAllNotificationsManager
import com.backend.managers.notificationsManager.GetNextNotificationManager
import com.backend.managers.notificationsManager.UpdateNotificationManager
import com.example.api.models.NotificationModel
import com.example.api.models.UpdateNotificationModel
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
            manager.invoke(userId, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun updateNotification(model: UpdateNotificationModel): HttpStatusCode {
        val manager: UpdateNotificationManager by inject()
        return runCatching {
            manager.invoke(Unit, model)
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
            manager.invoke(userId, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }
}
