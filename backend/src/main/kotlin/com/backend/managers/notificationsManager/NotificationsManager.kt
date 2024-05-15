package com.backend.managers.notificationsManager

import com.example.api.models.NotificationModel
import com.example.api.models.UpdateNotificationModel
import com.example.managers.SimpleManager
import io.ktor.http.HttpStatusCode
import java.util.UUID

interface GetAllNotificationsManager : NotificationsManager<UUID, Unit, List<NotificationModel>>
interface UpdateNotificationManager : NotificationsManager<Unit, UpdateNotificationModel, HttpStatusCode>
interface GetNextNotificationManager : NotificationsManager<UUID, Unit, NotificationModel>

interface NotificationsManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
