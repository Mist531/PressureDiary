package com.mist.common.data.repository

import arrow.core.Either
import com.example.api.ApiRoutes
import com.example.api.models.NotificationModel
import com.example.api.models.UpdateNotificationModel
import com.mist.common.modules.HTTP_CLIENT_AUTH
import com.mist.common.utils.BaseRepository
import com.mist.common.utils.errorflow.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface NotificationsRepository {
    suspend fun getAllNotifications(): Either<NetworkError, List<NotificationModel>>
    suspend fun updateNotification(
        model: UpdateNotificationModel
    ): Either<NetworkError, HttpStatusCode>

    suspend fun getNextNotification(): Either<NetworkError, NotificationModel>
}

class NotificationsRepositoryImpl: BaseRepository(), NotificationsRepository {
    private val client: HttpClient by inject(named(HTTP_CLIENT_AUTH))

    override suspend fun getAllNotifications(): Either<NetworkError, List<NotificationModel>> =
        withContext(repositoryScope.coroutineContext) {
            client.get(ApiRoutes.Notifications.GET_ALL)
                .handleResponse<List<NotificationModel>>()
        }

    override suspend fun updateNotification(
        model: UpdateNotificationModel
    ): Either<NetworkError, HttpStatusCode> =
        withContext(repositoryScope.coroutineContext) {
            client.put(ApiRoutes.Notifications.UPDATE) {
                setBody(model)
            }.handleResponse<HttpStatusCode>()
        }

    override suspend fun getNextNotification(): Either<NetworkError, NotificationModel> =
        withContext(repositoryScope.coroutineContext) {
            client.get(ApiRoutes.Notifications.GET_NEXT)
                .handleResponse<NotificationModel>()
        }
}