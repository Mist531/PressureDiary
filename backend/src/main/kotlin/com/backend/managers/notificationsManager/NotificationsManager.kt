package com.backend.managers.notificationsManager

import com.backend.managers.SimpleManager
import com.example.api.models.NotificationModel
import com.example.api.models.UpdateNotificationModel
import io.ktor.http.HttpStatusCode
import java.util.UUID

interface GetAllNotificationsManager : NotificationsManager<UUID, Unit, List<NotificationModel>>
interface UpdateNotificationManager :
    NotificationsManager<Unit, UpdateNotificationModel, HttpStatusCode>

interface GetNextNotificationManager : NotificationsManager<UUID, Unit, NotificationModel>

interface NotificationsManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
