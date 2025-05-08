package io.pressurediary.server.backend.managers.notificationsManager

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.NotificationModel
import io.pressurediary.server.api.models.UpdateNotificationModel
import io.pressurediary.server.backend.managers.SimpleManager
import java.util.UUID

interface GetAllNotificationsManager : NotificationsManager<UUID, Unit, List<NotificationModel>>
interface UpdateNotificationManager :
    NotificationsManager<Unit, UpdateNotificationModel, HttpStatusCode>

interface GetNextNotificationManager : NotificationsManager<UUID, Unit, NotificationModel>

interface NotificationsManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
