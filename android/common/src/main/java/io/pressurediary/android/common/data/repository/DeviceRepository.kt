package io.pressurediary.android.common.data.repository

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.pressurediary.android.common.modules.HTTP_CLIENT
import io.pressurediary.android.common.utils.BaseRepository
import io.pressurediary.android.common.utils.errorflow.NetworkError
import io.pressurediary.server.api.ApiRoutes
import io.pressurediary.server.api.models.DeleteUserDeviceModel
import io.pressurediary.server.api.models.DeviceModel
import io.pressurediary.server.api.models.PostDeviceForUserModel
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

class DeviceRepositoryImpl() : BaseRepository(), DeviceRepository {
    private val client: HttpClient by inject(named(HTTP_CLIENT))

    override suspend fun getUserDevicesList(
    ): Either<NetworkError, List<DeviceModel>> = repositoryContext {
        client.get(ApiRoutes.Device.GET_ALL)
            .handleResponse<List<DeviceModel>>()
    }

    override suspend fun addDeviceForUser(
        model: PostDeviceForUserModel
    ): Either<NetworkError, Unit> = repositoryContext {
        client.post(ApiRoutes.Device.ADD) {
            setBody(model)
        }.handleResponse<Unit>()
    }

    override suspend fun deleteUserDevice(
        model: DeleteUserDeviceModel
    ): Either<NetworkError, Unit> = repositoryContext {
        client.delete(ApiRoutes.Device.DELETE) {
            setBody(model)
        }.handleResponse<Unit>()
    }
}