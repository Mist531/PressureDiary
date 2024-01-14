package com.mist.common.data.repository

import arrow.core.Either
import com.example.api.ApiRoutes
import com.example.api.models.DeleteUserDeviceModel
import com.example.api.models.DeviceModel
import com.example.api.models.PostDeviceForUserModel
import com.mist.common.modules.HTTP_CLIENT_AUTH
import com.mist.common.utils.BaseRepository
import com.mist.common.utils.errorflow.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.withContext
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface DeviceRepository {
    suspend fun getUserDevicesList(): Either<NetworkError, List<DeviceModel>>
    suspend fun addDeviceForUser(
        model: PostDeviceForUserModel
    ): Either<NetworkError, Unit>

    suspend fun deleteUserDevice(
        model: DeleteUserDeviceModel
    ): Either<NetworkError, Unit>
}

class DeviceRepositoryImpl: BaseRepository(), DeviceRepository {
    private val client: HttpClient by inject(named(HTTP_CLIENT_AUTH))

    override suspend fun getUserDevicesList(): Either<NetworkError, List<DeviceModel>> =
        withContext(repositoryScope.coroutineContext) {
            client.get(ApiRoutes.Device.GET_ALL)
                .handleResponse<List<DeviceModel>>()
        }

    override suspend fun addDeviceForUser(
        model: PostDeviceForUserModel
    ): Either<NetworkError, Unit> = withContext(repositoryScope.coroutineContext) {
        client.post(ApiRoutes.Device.ADD) {
            setBody(model)
        }.handleResponse<Unit>()
    }

    override suspend fun deleteUserDevice(
        model: DeleteUserDeviceModel
    ): Either<NetworkError, Unit> = withContext(repositoryScope.coroutineContext) {
        client.delete(ApiRoutes.Device.DELETE) {
            setBody(model)
        }.handleResponse<Unit>()
    }
}