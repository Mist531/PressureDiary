package io.pressurediary.android.common.data.repository

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.pressurediary.android.common.modules.HTTP_CLIENT
import io.pressurediary.android.common.utils.BaseRepository
import io.pressurediary.android.common.utils.errorflow.NetworkError
import io.pressurediary.server.api.ApiRoutes
import io.pressurediary.server.api.models.NotificationModel
import io.pressurediary.server.api.models.UpdateNotificationModel
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface NotificationsRepository {
    suspend fun getAllNotifications(): Either<NetworkError, List<NotificationModel>>
    suspend fun updateNotification(
        model: UpdateNotificationModel
    ): Either<NetworkError, HttpStatusCode>

    suspend fun getNextNotification(): Either<NetworkError, NotificationModel>
}

class NotificationsRepositoryImpl() : BaseRepository(), NotificationsRepository {
    private val client: HttpClient by inject(named(HTTP_CLIENT))

    override suspend fun getAllNotifications(
    ): Either<NetworkError, List<NotificationModel>> = repositoryContext {
        client.get(ApiRoutes.Notifications.GET_ALL)
            .handleResponse<List<NotificationModel>>()
    }

    override suspend fun updateNotification(
        model: UpdateNotificationModel
    ): Either<NetworkError, HttpStatusCode> = repositoryContext {
        client.put(ApiRoutes.Notifications.UPDATE) {
            setBody(model)
        }.handleResponse<HttpStatusCode>()
    }

    override suspend fun getNextNotification(
    ): Either<NetworkError, NotificationModel> = repositoryContext {
        client.get(ApiRoutes.Notifications.GET_NEXT)
            .handleResponse<NotificationModel>()
    }
}