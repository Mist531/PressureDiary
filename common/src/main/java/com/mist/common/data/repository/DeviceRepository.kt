package com.mist.common.data.repository

import arrow.core.Either
import com.example.api.ApiRoutes
import com.example.api.models.DeleteUserDeviceModel
import com.example.api.models.DeviceModel
import com.example.api.models.PostDeviceForUserModel
import com.mist.common.utils.BaseRepository
import com.mist.common.utils.errorflow.NetworkError
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.withContext

interface DeviceRepository {
    suspend fun getUserDevicesList(): Either<NetworkError, List<DeviceModel>>
    suspend fun addDeviceForUser(
        model: PostDeviceForUserModel
    ): Either<NetworkError, Unit>

    suspend fun deleteUserDevice(
        model: DeleteUserDeviceModel
    ): Either<NetworkError, Unit>
}

class DeviceRepositoryImpl(
    private val client: HttpClient
) : BaseRepository(), DeviceRepository {
    override suspend fun getUserDevicesList(): Either<NetworkError, List<DeviceModel>> =
        withContext(repositoryScope.coroutineContext) {
            client.get(ApiRoutes.Device.GET_ALL)
                .handleResponse<List<DeviceModel>>()
        }


    override suspend fun addDeviceForUser(
        model: PostDeviceForUserModel
    ): Either<NetworkError, Unit> = withContext(repositoryScope.coroutineContext) {
        client.post(
            urlString = ApiRoutes.Device.ADD,
        ) {
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