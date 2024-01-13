package com.mist.common.data.repository

import arrow.core.Either
import com.example.api.ApiRoutes
import com.example.api.models.NotificationModel
import com.example.api.models.UpdateNotificationModel
import com.mist.common.utils.BaseRepository
import com.mist.common.utils.errorflow.NetworkError
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.withContext

interface NotificationsRepository {
    suspend fun getAllNotifications(): Either<NetworkError, List<NotificationModel>>
    suspend fun updateNotification(
        model: UpdateNotificationModel
    ): Either<NetworkError, HttpStatusCode>

    suspend fun getNextNotification(): Either<NetworkError, NotificationModel>
}

class NotificationsRepositoryImpl(
    private val client: HttpClient
) : BaseRepository(), NotificationsRepository {
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